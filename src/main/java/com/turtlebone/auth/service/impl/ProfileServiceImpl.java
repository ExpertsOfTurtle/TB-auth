
package com.turtlebone.auth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.turtlebone.auth.entity.Profile;
import com.turtlebone.auth.repository.ProfileRepository;
import com.turtlebone.auth.model.ProfileModel;
import com.turtlebone.auth.service.ProfileService;
import com.turtlebone.core.util.BeanCopyUtils;

@Service
public class ProfileServiceImpl implements ProfileService {


	@Autowired
	private ProfileRepository profileRepo;
	

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return profileRepo.deleteByPrimaryKey(id);
	}
	

    /*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public ProfileModel findByPrimaryKey(Integer id) {
		Profile profile = profileRepo.selectByPrimaryKey(id);
		return BeanCopyUtils.map(profile, ProfileModel.class);
	}
	
	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int updateByPrimaryKey(ProfileModel profileModel) {
		return profileRepo.updateByPrimaryKey(BeanCopyUtils.map(profileModel, Profile.class));
	}
	
	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int updateByPrimaryKeySelective(ProfileModel profileModel) {
		return profileRepo.updateByPrimaryKeySelective(BeanCopyUtils.map(profileModel, Profile.class));
	}
	

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int create(ProfileModel profileModel) {
		return profileRepo.insert(BeanCopyUtils.map(profileModel, Profile.class));
	}

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int createSelective(ProfileModel profileModel) {
		return profileRepo.insertSelective(BeanCopyUtils.map(profileModel, Profile.class));
	}

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int selectCount(ProfileModel profileModel) {
		return profileRepo.selectCount(BeanCopyUtils.map(profileModel, Profile.class));
	}


	@Override
	public ProfileModel selectByUsername(String username) {
		Profile profile = profileRepo.selectByUsername(username);
		return BeanCopyUtils.map(profile, ProfileModel.class); 
	}



}
