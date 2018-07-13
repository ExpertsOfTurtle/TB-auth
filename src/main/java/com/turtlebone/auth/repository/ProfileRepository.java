package com.turtlebone.auth.repository;

import com.turtlebone.auth.entity.Profile;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository{

  	int deleteByPrimaryKey(Integer id);
	
	Profile selectByPrimaryKey(Integer id);
	
	    
    int updateByPrimaryKey(Profile profile);

    int updateByPrimaryKeySelective(Profile profile);

  	int insert(Profile profile);
  	
	int insertSelective(Profile profile);


    int selectCount(Profile profile);

    List<Profile> selectPage(@Param("profile") Profile profile, @Param("pageable") Pageable pageable);
	
    Profile selectByUsername(String username);
}