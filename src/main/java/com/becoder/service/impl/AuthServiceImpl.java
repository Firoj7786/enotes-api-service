package com.becoder.service.impl;

import java.net.http.HttpRequest;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

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
import com.becoder.dto.UserReqDto;
import com.becoder.dto.UserResDto;
import com.becoder.model.Role;
import com.becoder.model.User;
import com.becoder.repository.RoleRepository;
import com.becoder.repository.AuthRepository;
import com.becoder.service.JwtService;
import com.becoder.service.AuthService;
import com.becoder.util.Validation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

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
    
	private static final String SECRET_KEY = "MySuperSecretKey123!";
	@Override
	public boolean registerUser(UserReqDto userDto) throws Exception {
	
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

	private void sendEmail(UserReqDto userDto, User saveUser) throws Exception {
		String message = "Hi,<b>" + saveUser.getFirstName() + "</b> " + "<br> Your account register sucessfully.<br>"
				+ "<br> Click the below link verify & Active your account <br>" + "<a href='#'>Click Here</a> <br><br>"
				+ "Thanks,<br>Enotes.com";
		EmailRequest email = EmailRequest.builder().to(userDto.getEmail()).title("Account Creating Confirmation")
				.subject("Account Created Success").message(message).build();

		emailService.sendEmail(email);

	}

	private void setRole(UserReqDto userDto, User user) {
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

			LoginResponse loginResponse=LoginResponse.builder()
					.user(mapper.map(customUserDetails.getUser(), UserResDto.class))
					.token(token)
					.build();
			return loginResponse;
		}

		return null;
	}
    @Override
    public boolean forgotPassword(String email, HttpServletRequest request) throws Exception {
        User user = authRepository.findByEmail(email);
        if (user == null) return false;

        String token = generateResetToken(user.getId());
        String resetUrl = request.getScheme() + "://" + request.getServerName() + "/reset-password?token=" + token;
        sendEmailRequest(user, resetUrl);
        return true;
    }

    private String generateResetToken(Integer userId) {
        long timestamp = System.currentTimeMillis();
        String data = userId + ":" + timestamp;
        String signature = calculateHmac(data);
        return Base64.getUrlEncoder().encodeToString((data + ":" + signature).getBytes());
    }
    private void sendEmailRequest(User user, String resetUrl) throws Exception {
        String message = "Hi <b>[[username]]</b>,<br>" 
                         + "<p>You have requested to reset your password.</p>"
                         + "<p>Click the link below to change your password:</p>"
                         + "<p><a href='[[url]]'>Change my password</a></p>"
                         + "<p>Ignore this email if you remember your password "
                         + "or did not make this request.</p><br>"
                         + "Thanks,<br>Enotes.com";

        message = message.replace("[[username]]", user.getFirstName());
        message = message.replace("[[url]]", resetUrl);

        EmailRequest emailRequest = EmailRequest.builder()
            .to(user.getEmail())
            .title("Password Reset")
            .subject("Password Reset Link")
            .message(message)
            .build();

        emailService.sendEmail(emailRequest);
    }
    private String calculateHmac(String data) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(SECRET_KEY.getBytes(), "HmacSHA256"));
            byte[] hmac = mac.doFinal(data.getBytes());
            return Base64.getUrlEncoder().encodeToString(hmac);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate HMAC", e);
        }
    }

	@Override
    public boolean resetPassword(String token, String newPassword) throws Exception {
		//try {
            String decoded = new String(Base64.getUrlDecoder().decode(token));
            String[] parts = decoded.split(":");
            if (parts.length != 3) return false;

            Long userId = Long.parseLong(parts[0]);
            long timestamp = Long.parseLong(parts[1]);
            String signature = parts[2];

            if (System.currentTimeMillis() - timestamp > 3600000) return false;

            String data = userId + ":" + timestamp;
            if (!calculateHmac(data).equals(signature)) return false;

            User user = authRepository.findById(userId);
            if (user == null) return false;

            user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
              User saved = authRepository.save(user);
            if (ObjectUtils.isEmpty(saved)) return true;
            return false;
    }
}
