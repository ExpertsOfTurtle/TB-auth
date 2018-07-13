package com.turtlebone.auth.repository;

import com.turtlebone.auth.entity.Token;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository{

  	int deleteByPrimaryKey(Integer id);
	
	Token selectByPrimaryKey(Integer id);
	
	    
    int updateByPrimaryKey(Token token);

    int updateByPrimaryKeySelective(Token token);

  	int insert(Token token);
  	
	int insertSelective(Token token);

    int selectCount(Token token);

    List<Token> selectPage(@Param("token") Token token, @Param("pageable") Pageable pageable);
    
    Token selectByTokenId(String tokenId);
}