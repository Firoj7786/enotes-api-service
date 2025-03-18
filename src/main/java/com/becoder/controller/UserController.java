package com.becoder.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.becoder.dto.ChangePasswordReq;
import com.becoder.dto.UserResDto;
import com.becoder.model.User;
import com.becoder.service.UserService;
import com.becoder.util.CommonUtils;

@RestController
@RequestMapping("api/v1/userPro")
public class UserController {
	    @Autowired
	    ModelMapper modelmapper;
	    @Autowired
	    UserService userService;
	    @GetMapping("/profile")
	   ResponseEntity<?> getUserProfile() {
		     User user = CommonUtils.getLoggedInUser();
			 UserResDto userRes  = modelmapper.map(user,UserResDto.class);
             return CommonUtils.createBuildResponse(userRes, HttpStatus.OK);
	   }
	    
	   @PostMapping("/changePassword")
	   ResponseEntity<?>  changePassword(ChangePasswordReq changePass) {
		   userService.changePassword(changePass);
		   return CommonUtils.createBuildResponseMessage("Password changed Succesfully", HttpStatus.OK);   
	   }
	

}
