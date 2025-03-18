package com.becoder.service;

import com.becoder.dto.LoginRequest;
import com.becoder.dto.LoginResponse;
import com.becoder.dto.UserReqDto;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthService  {

	boolean registerUser(UserReqDto userDto) throws Exception;
	public LoginResponse login(LoginRequest loginRequest);
	boolean forgotPassword(String email, HttpServletRequest request) throws Exception;
	boolean resetPassword(String token, String newPassword) throws Exception;
}
