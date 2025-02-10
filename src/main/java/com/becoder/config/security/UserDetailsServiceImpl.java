package com.becoder.config.security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.becoder.model.User;
import com.becoder.repository.AuthRepository;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private AuthRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	User user	= userRepo.findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException("invalid email");
		}

		return new CustomUserDetails(user);
	}

	

}
