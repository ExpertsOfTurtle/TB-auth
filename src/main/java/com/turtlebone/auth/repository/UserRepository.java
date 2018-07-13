package com.turtlebone.auth.repository;

import com.turtlebone.auth.entity.User;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository{

  	int deleteByPrimaryKey(Integer id);
	
	User selectByPrimaryKey(Integer id);
	
	    
    int updateByPrimaryKey(User user);

    int updateByPrimaryKeySelective(User user);

  	int insert(User user);
  	
	int insertSelective(User user);


    int selectCount(User user);

    List<User> selectPage(@Param("user") User user, @Param("pageable") Pageable pageable);

    User selectByUsername(String username);
    
    List<User> selectByUserList(List<String> userList);
    
    List<User> listAllUser();
    
    User selectByCondition(Map<String, String> map);
}