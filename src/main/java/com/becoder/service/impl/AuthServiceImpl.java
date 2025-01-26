package com.becoder.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.becoder.dto.UserDto;
import com.becoder.model.Role;
import com.becoder.model.User;
import com.becoder.repository.AuthRepository;
import com.becoder.repository.RoleRepository;
import com.becoder.service.AuthService;
import com.becoder.util.Validation;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    Validation validation;
    
	@Autowired
	AuthRepository authRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	ModelMapper modelMapper;

	@Override
	public boolean registerUser(UserDto userDto) {
		//User Validation 
		  validation.userValidation(userDto);
		User user = modelMapper.map(userDto, User.class);
		//user.setCreatedBy(2);
		setRole(userDto,user);
		authRepository.save(user);
		if (!ObjectUtils.isEmpty(user)) {
			return true;
		} else {
			return false;
		}
	}

	private void setRole(UserDto userDto, User user) {
		          List <Integer> roleIds = userDto.getRoles().stream().map(r -> r.getId()).toList();
		           System.out.println("This are role ids"+ roleIds);
		            List<Role> roles= roleRepository.findAllById(roleIds);
		            user.setRoles(roles);
	}

}
