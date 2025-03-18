package com.becoder.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.becoder.dto.ChangePasswordReq;
import com.becoder.model.User;
import com.becoder.repository.UserRepository;
import com.becoder.service.UserService;
import com.becoder.util.CommonUtils;
@Service
public class UserServiceImpl implements UserService {
     @Autowired
    private UserRepository userRepo;
     @Autowired
 	 private PasswordEncoder passwordEncoder;
	@Override
	public void changePassword(ChangePasswordReq changePass) {
		User logedInUser = CommonUtils.getLoggedInUser();

		if (!passwordEncoder.matches(changePass.getOldPassword(), logedInUser.getPassword())) {
			throw new IllegalArgumentException("Old Password is incorrect !!");
		}
		String encodePassword = passwordEncoder.encode(changePass.getNewPassword());
		logedInUser.setPassword(encodePassword);
		userRepo.save(logedInUser);
	}

}
