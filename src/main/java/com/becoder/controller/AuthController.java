package com.becoder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.becoder.dto.LoginRequest;
import com.becoder.dto.LoginResponse;
import com.becoder.dto.UserDto;
import com.becoder.service.AuthService;
import com.becoder.util.CommonUtils;

@RestController
@RequestMapping("api/v1/user")
public class AuthController {
	@Autowired
	AuthService authService;
    
	@PostMapping("/save")
	ResponseEntity<?> registerUser(@RequestBody UserDto userDto) throws Exception {
		boolean user = authService.registerUser(userDto);
		if (user) {
			return CommonUtils.createBuildResponseMessage("User Created Successfully", HttpStatus.OK);
		}
		return CommonUtils.createBuildResponseMessage("User Creation Failed", HttpStatus.BAD_REQUEST);
	}
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception {

		LoginResponse loginResponse = authService.login(loginRequest);
		if (ObjectUtils.isEmpty(loginResponse)) {
			return CommonUtils.createErrorResponseMessage("invalid credential", HttpStatus.BAD_REQUEST);
		}
		return CommonUtils.createBuildResponse(loginResponse,HttpStatus.OK);
	}
}