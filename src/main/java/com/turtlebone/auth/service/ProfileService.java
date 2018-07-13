
package com.turtlebone.auth.service;

import com.turtlebone.auth.model.ProfileModel;
import java.util.Date;

public interface ProfileService{
	
	public int create(ProfileModel profileModel);
	
	public int createSelective(ProfileModel profileModel);
	
	public ProfileModel findByPrimaryKey(Integer id);
	
	public int updateByPrimaryKey(ProfileModel profileModel);
	
	public int updateByPrimaryKeySelective(ProfileModel profileModel);
	
	public int deleteByPrimaryKey(Integer id);
	
	public int selectCount(ProfileModel profileModel);
	
	public ProfileModel selectByUsername(String username);
}