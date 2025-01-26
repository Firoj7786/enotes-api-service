package com.becoder.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.becoder.dto.CategoryDto;
import com.becoder.dto.UserDto;
import com.becoder.exception.ExistDataException;
import com.becoder.exception.ValidationException;
import com.becoder.repository.AuthRepository;
import com.becoder.repository.RoleRepository;

import org.springframework.util.StringUtils;

@Component
public class Validation {

	@Autowired
	private RoleRepository roleRepo;
	@Autowired
	private AuthRepository userRepo;

	public void categoryValidation(CategoryDto categoryDto) {

		Map<String, Object> error = new LinkedHashMap<>();

		if (ObjectUtils.isEmpty(categoryDto)) {
			throw new IllegalArgumentException("category Object/JSON shouldn't be null or empty");
		} else {

			// validation name field
			if (ObjectUtils.isEmpty(categoryDto.getName())) {
				error.put("name", "name field is empty or null");
			} else {
				if (categoryDto.getName().length() < 3) {
					error.put("name", "name length min 3");
				}
				if (categoryDto.getName().length() > 100) {
					error.put("name", "name length max 100");
				}
			}

			// validation description
			if (ObjectUtils.isEmpty(categoryDto.getDescription())) {
				error.put("description", "description field is empty or null");
			}

			// validation isActive
			if (ObjectUtils.isEmpty(categoryDto.isActive())) {
				error.put("isActive", "isActive field is empty or null");
			} else {
				if (categoryDto.isActive() != Boolean.TRUE.booleanValue()
						&& categoryDto.isActive() != Boolean.FALSE.booleanValue()) {
					error.put("isActive", "invalid value isActive field ");
				}
			}
		}

		if (!error.isEmpty()) {
			throw new ValidationException(error);
		}

	}
	
	public void userValidation(UserDto userDto) {

		if (!StringUtils.hasText(userDto.getFirstName())) {
			throw new IllegalArgumentException("first name is invalid");
		}

		if (!StringUtils.hasText(userDto.getLastName())) {
			throw new IllegalArgumentException("last name is invalid");
		}

		if (!StringUtils.hasText(userDto.getEmail()) || !userDto.getEmail().matches(Constants.EMAIL_REGEX)) {
			throw new IllegalArgumentException("email is invalid");
		}else {
			boolean emailPresent = userRepo.existsByEmail(userDto.getEmail());
			if(emailPresent) {
				    throw new ExistDataException("Email already exist");
			}
		}

		if (!StringUtils.hasText(userDto.getMobNo()) || !userDto.getMobNo().matches(Constants.MOBNO_REGEX)) {
			throw new IllegalArgumentException("mobno is invalid");
		}
		if (!StringUtils.hasText(userDto.getPassword()) || !userDto.getPassword().matches(Constants.Password_REGEX)) {
			throw new IllegalArgumentException("a digit must occur at least"
					+ "a lower case letter must occur at least once"
					+ "an upper case letter must occur at least once"
					+ " a special character must occur at least once"
					+ "no whitespace allowed in the entire string"
					+ "anything, at least eight places though");
		}

		if (CollectionUtils.isEmpty(userDto.getRoles())) {
			throw new IllegalArgumentException("role is invalid");
		} else {

			List<Integer> roleIds = roleRepo.findAll().stream().map(r -> r.getId()).toList();

			List<Integer> invalidReqRoleids = userDto.getRoles().stream().map(r -> r.getId())
					.filter(roleId -> !roleIds.contains(roleId)).toList();

			if (!CollectionUtils.isEmpty(invalidReqRoleids)) {
				throw new IllegalArgumentException("role is invalid" + invalidReqRoleids);
			}
		}
	}
	
	
	
	
	

}