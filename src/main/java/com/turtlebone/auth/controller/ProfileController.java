package com.turtlebone.auth.controller;


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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.turtlebone.auth.bean.ProfileRequest;
import com.turtlebone.auth.model.ProfileModel;
import com.turtlebone.auth.service.ProfileService;
import com.turtlebone.core.util.MD5Util;
import com.turtlebone.core.util.StringUtil;

@Controller
@EnableAutoConfiguration
@RequestMapping(value="/profile")
public class ProfileController {
	private static Logger logger = LoggerFactory.getLogger(ProfileController.class);
	
	@Autowired
	private ProfileService profileService;
	
	@RequestMapping(value="/create", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> create(@RequestBody ProfileRequest request) {
		logger.info("creating user:{}", request.getUsername());
	
		if (StringUtil.isEmpty(request.getPassword())) {
			logger.warn("Fail! Missing password");
			return ResponseEntity.ok("Password is required");
		} else if (StringUtil.isEmpty(request.getUsername())) {
			logger.warn("Fail! Missing username");
			return ResponseEntity.ok("Username is required");
		} else if (StringUtil.isEmpty(request.getEmail())) {
			logger.warn("Fail! Missing email");
			return ResponseEntity.ok("Email is required");
		}
		
		ProfileModel profile = new ProfileModel();
		profile.setUsername(request.getUsername());
		profile.setEmail(request.getEmail());
		String pw = MD5Util.md5(request.getUsername() + request.getPassword()); 
		profile.setPassword(pw);
		profile.setStatus(0);
		profile.setActive(1);
		
		int rt = profileService.create(profile);
		
		if (rt == 1) {
			logger.info("Create done!");
			return ResponseEntity.ok("OK");
		} else {
			logger.warn("Fail! But I don't know why!");
			return ResponseEntity.ok("FAIL");
		}
	}
	
	@RequestMapping(value="/modify", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> modify(@RequestBody ProfileRequest request) {
		logger.info("modifying user:{}", request.getUsername());
	
		if (StringUtil.isEmpty(request.getPassword())) {
			logger.warn("Fail! Missing password");
			return ResponseEntity.ok("Password is required");
		} else if (StringUtil.isEmpty(request.getUsername())) {
			logger.warn("Fail! Missing username");
			return ResponseEntity.ok("Username is required");
		} else if (StringUtil.isEmpty(request.getEmail())) {
			logger.warn("Fail! Missing email");
			return ResponseEntity.ok("Email is required");
		}
		
		ProfileModel profile = profileService.selectByUsername(request.getUsername());
		
		if (profile == null) {
			logger.warn("user {} not found", request.getUsername());
			return ResponseEntity.ok("user not exist");
		}
		profile.setEmail(request.getEmail());
		String pw = MD5Util.md5(request.getUsername() + request.getPassword()); 
		profile.setPassword(pw);
		profile.setStatus(0);
		profile.setActive(1);
		
		int rt = profileService.updateByPrimaryKey(profile);
		
		if (rt == 1) {
			logger.info("Modify done!");
			return ResponseEntity.ok("OK");
		} else {
			logger.warn("Fail! But I don't know why!");
			return ResponseEntity.ok("FAIL");
		}
	}
	
}
