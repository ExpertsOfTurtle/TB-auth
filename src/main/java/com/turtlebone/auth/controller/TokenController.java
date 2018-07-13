package com.turtlebone.auth.controller;


import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.turtlebone.auth.bean.CreateTokenRequest;
import com.turtlebone.auth.bean.RenewTokenRequest;
import com.turtlebone.auth.bean.VerifyTokenRequest;
import com.turtlebone.auth.exception.AuthException;
import com.turtlebone.auth.model.ProfileModel;
import com.turtlebone.auth.model.TokenModel;
import com.turtlebone.auth.model.UserModel;
import com.turtlebone.auth.service.ProfileService;
import com.turtlebone.auth.service.TokenService;
import com.turtlebone.auth.service.UserService;
import com.turtlebone.core.bean.UserDetails;
import com.turtlebone.core.exception.TurtleException;
import com.turtlebone.core.service.RedisService;
import com.turtlebone.core.util.DateUtil;
import com.turtlebone.core.util.MD5Util;
import com.turtlebone.core.util.StringUtil;

@Controller
@EnableAutoConfiguration
@RequestMapping(value="/token")
public class TokenController {
	private static Logger logger = LoggerFactory.getLogger(TokenController.class);
	
	@Autowired
	private ProfileService profileService;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private RedisService redisService;
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/create", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> create(@RequestBody CreateTokenRequest request) {
		logger.info("creating token:{}", request.getUsername());
	
		if (StringUtil.isEmpty(request.getUsername())) {
			logger.warn("Fail! Missing username");
			return ResponseEntity.ok("Username is required");
		} else if (StringUtil.isEmpty(request.getDatetime())) {
			logger.warn("Fail! Missing datetime");
			return ResponseEntity.ok("Datetime is required");
		} else if (StringUtil.isEmpty(request.getSignData())) {
			logger.warn("Fail! Missing SignData");
			return ResponseEntity.ok("SignData is required");
		}
		
		ProfileModel profile = profileService.selectByUsername(request.getUsername());
		if (profile == null) {
			logger.warn("user {} not found", request.getUsername());
			return ResponseEntity.ok("用户不存在");
		}
		
		Date createDate = null;
		Date current = new Date();
		try {
			createDate = DateUtil.parse(request.getDatetime(), "yyyyMMddHHmmss");
		} catch (ParseException e) {
			logger.warn("Date format incorrect, date={}", request.getDatetime());
			return ResponseEntity.ok("Datetime should be in yyyyMMddHHmmss format");
		}
		if (Math.abs(createDate.getTime() - current.getTime()) > 1000 * 60) {
			//时间误差超过60s
			logger.warn("Timeout");
			return ResponseEntity.ok("超时了");
		}
		
		String pw = profile.getPassword();
		String text = request.getDatetime() + pw;
		String sign = MD5Util.md5(text);
		if (!sign.equalsIgnoreCase(request.getSignData())) {
			logger.warn("校验失败 [{},{}]", sign, request.getSignData());
			return ResponseEntity.ok("校验失败");
		}
		
		TokenModel token = new TokenModel();
		token.setCreatetime(current);
		token.setExpirytime(new Date(current.getTime() + 5 * 1000 * 60));
		token.setUsername(request.getUsername());
		String tokenId = getTokenId();
		token.setTokenid(tokenId);
		
		int rt = tokenService.create(token);
		
		if (rt == 1) {
			logger.info("Create done!");
			putToRedis(token);
			return ResponseEntity.ok(token);
		} else {
			logger.warn("Fail! But I don't know why!");
			return ResponseEntity.ok("FAIL");
		}
	}
	
	@RequestMapping(value="/verify", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> verify(@RequestBody VerifyTokenRequest request) throws TurtleException {
		logger.info("Verify [username={},tokenId={}]", request.getUsername(), request.getTokenId());
		TokenModel token = null;
		try {
			token = tokenService.verifyToken(request.getTokenId(), request.getUsername());
		} catch (AuthException e) {
			String msg = e.getErrorMesage();
			logger.error(msg);
			throw new TurtleException("E0000", msg);
		}
		logger.info("Verification successfully! {}", token);
		return ResponseEntity.ok(token);
	}
	@RequestMapping(value="/verify", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> verify(@RequestParam String username, @RequestParam String tokenId) {
		logger.info("Verify [username={},tokenId={}]", username, tokenId);
		TokenModel token = null;
		try {
			token = tokenService.verifyToken(tokenId, username);
		} catch (AuthException e) {
			String msg = e.getErrorMesage();
			logger.error(msg);
			return ResponseEntity.ok(msg);
		}
		logger.info("Verification successfully! {}", token);
		return ResponseEntity.ok("OK");
	}
	@RequestMapping(value="/renew", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> renew(@RequestBody RenewTokenRequest request) {
		TokenModel token = null;
		try {
			token = tokenService.verifyToken(request.getTokenId(), request.getUsername());
		} catch (AuthException e) {
			String msg = e.getErrorMesage();
			logger.error(msg);
			return ResponseEntity.ok(msg);
		}
		
		Long second = request.getExtendSecond();
		if (second == null || second <= 0) {
			logger.warn("extendSceond[{}] incorrect, default set to 10min", second);
			second = 600L;
		}
		
		Date date = new Date();
		date.setTime(date.getTime() + second * 1000);
		token.setExpirytime(date);
		tokenService.updateByPrimaryKey(token);
		logger.info("Token[{}] has been renewed to [{}]", request.getTokenId(), date);
		
		return ResponseEntity.ok("OK");
	}
	
	private String getTokenId() {
		String str = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);
		return str;
	}	
	
	private void putToRedis(TokenModel token) {
		UserModel um = userService.selectByUsername(token.getUsername());
		UserDetails user = new UserDetails();
		user.setTokenType("TB");
		user.setTokenId(token.getTokenid());
		user.setLoginName(token.getUsername());
		user.setNickName(um.getNickname());
		
		redisService.set(token.getTokenid(), JSON.toJSONString(user));
	}
}
