
package com.turtlebone.auth.service;

import com.turtlebone.auth.exception.AuthException;
import com.turtlebone.auth.model.TokenModel;

public interface TokenService{
	
	public int create(TokenModel tokenModel);
	
	public int createSelective(TokenModel tokenModel);
	
	public TokenModel findByPrimaryKey(Integer id);
	
	public int updateByPrimaryKey(TokenModel tokenModel);
	
	public int updateByPrimaryKeySelective(TokenModel tokenModel);
	
	public int deleteByPrimaryKey(Integer id);
	
	public int selectCount(TokenModel tokenModel);

	public TokenModel selectByTokenId(String tokenId);
	
	public TokenModel verifyToken(String tokenId, String username) throws AuthException;
}