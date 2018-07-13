
package com.turtlebone.auth.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turtlebone.auth.entity.Token;
import com.turtlebone.auth.exception.AuthException;
import com.turtlebone.auth.repository.TokenRepository;
import com.turtlebone.auth.model.TokenModel;
import com.turtlebone.auth.service.TokenService;
import com.turtlebone.core.util.BeanCopyUtils;
import com.turtlebone.core.util.StringUtil;

@Service
public class TokenServiceImpl implements TokenService {

	@Autowired
	private TokenRepository tokenRepo;
	

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return tokenRepo.deleteByPrimaryKey(id);
	}
	

    /*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public TokenModel findByPrimaryKey(Integer id) {
		Token token = tokenRepo.selectByPrimaryKey(id);
		return BeanCopyUtils.map(token, TokenModel.class);
	}
	
	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int updateByPrimaryKey(TokenModel tokenModel) {
		return tokenRepo.updateByPrimaryKey(BeanCopyUtils.map(tokenModel, Token.class));
	}
	
	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int updateByPrimaryKeySelective(TokenModel tokenModel) {
		return tokenRepo.updateByPrimaryKeySelective(BeanCopyUtils.map(tokenModel, Token.class));
	}
	

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int create(TokenModel tokenModel) {
		return tokenRepo.insert(BeanCopyUtils.map(tokenModel, Token.class));
	}

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int createSelective(TokenModel tokenModel) {
		return tokenRepo.insertSelective(BeanCopyUtils.map(tokenModel, Token.class));
	}

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int selectCount(TokenModel tokenModel) {
		return tokenRepo.selectCount(BeanCopyUtils.map(tokenModel, Token.class));
	}


	@Override
	public TokenModel selectByTokenId(String tokenId) {
		Token token = tokenRepo.selectByTokenId(tokenId);
		return BeanCopyUtils.map(token, TokenModel.class);
	}


	@Override
	public TokenModel verifyToken(String tokenId, String username) throws AuthException {
		if (StringUtil.isEmpty(username)) {
			throw new AuthException("Fail! Missing username");
			
		} else if (StringUtil.isEmpty(tokenId)) {
			throw new AuthException("tokenId is required");
		}
		
		TokenModel token = selectByTokenId(tokenId);
		if (token == null) {
			throw new AuthException("tokenId 不存在");
		}
		if (!token.getUsername().equals(username)) {
			throw new AuthException("username&tokenId信息不匹配");
		}
		if (token.getExpirytime() == null || token.getExpirytime().getTime() < new Date().getTime()) {
			throw new AuthException("tokenId 已经过期");
		}
		return token;
	}



}
