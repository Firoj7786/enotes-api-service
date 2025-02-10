package com.becoder.service.impl;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.becoder.config.security.CustomUserDetails;
import com.becoder.dto.EmailRequest;
import com.becoder.dto.LoginRequest;
import com.becoder.dto.LoginResponse;
import com.becoder.dto.UserDto;
import com.becoder.model.Role;
import com.becoder.model.User;
import com.becoder.repository.RoleRepository;
import com.becoder.repository.AuthRepository;
import com.becoder.service.JwtService;
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
	@Autowired
	EmailService emailService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private ModelMapper mapper;

	@Autowired
	private JwtService jwtService;

	@Override
	public boolean registerUser(UserDto userDto) throws Exception {
		// User Validation
		validation.userValidation(userDto);
		User user = modelMapper.map(userDto, User.class);
		// user.setCreatedBy(2);
		setRole(userDto, user);
		authRepository.save(user);
		if (!ObjectUtils.isEmpty(user)) {
			sendEmail(userDto, user);
			return true;
		} else {
			return false;
		}
	}

	private void sendEmail(UserDto userDto, User saveUser) throws Exception {
		String message = "Hi,<b>" + saveUser.getFirstName() + "</b> " + "<br> Your account register sucessfully.<br>"
				+ "<br> Click the below link verify & Active your account <br>" + "<a href='#'>Click Here</a> <br><br>"
				+ "Thanks,<br>Enotes.com";
		EmailRequest email = EmailRequest.builder().to(userDto.getEmail()).title("Account Creating Confirmation")
				.subject("Account Created Success").message(message).build();

		emailService.sendEmail(email);

	}

	private void setRole(UserDto userDto, User user) {
		List<Integer> roleIds = userDto.getRoles().stream().map(r -> r.getId()).toList();
		System.out.println("This are role ids" + roleIds);
		List<Role> roles = roleRepository.findAllById(roleIds);
		user.setRoles(roles);
	}

	@Override
	public LoginResponse login(LoginRequest loginRequest) {

		Authentication authenticate = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		if (authenticate.isAuthenticated()) {
			CustomUserDetails customUserDetails = (CustomUserDetails) authenticate.getPrincipal();

			String token = jwtService.generateToken(customUserDetails.getUser());

			LoginResponse loginResponse = LoginResponse.builder()
					.user(mapper.map(customUserDetails.getUser(), UserDto.class)).token(token).build();
			return loginResponse;
		}

		return null;
	}

}
