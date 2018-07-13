
package com.turtlebone.auth.service;

import java.util.List;

import com.turtlebone.auth.model.UserModel;

public interface UserService{
	
	public int create(UserModel userModel);
	
	public int createSelective(UserModel userModel);
	
	public UserModel findByPrimaryKey(Integer id);
	
	public int updateByPrimaryKey(UserModel userModel);
	
	public int updateByPrimaryKeySelective(UserModel userModel);
	
	public int deleteByPrimaryKey(Integer id);
	

	public int selectCount(UserModel userModel);
	
	public UserModel selectByUsername(String username);
	
	public List<UserModel> selectByUserList(List<String> userList);
	
	public List<UserModel> listAllUser();
	
	public UserModel selectByCondition(String username, String openId);
}