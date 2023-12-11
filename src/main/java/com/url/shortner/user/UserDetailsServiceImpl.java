package com.url.shortner.user;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 User user = userRepository.findByUsername(username)
	                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

	        return new org.springframework.security.core.userdetails.User(
	                user.getUsername(),
	                user.getPassword(),
	                Collections.emptyList()
	        );
	}

	public void registerUser(UserRegistrationRequest userRegistrationRequest) {
		User user = new User();
		user.setUsername(userRegistrationRequest.getUsername());
		user.setPassword(new BCryptPasswordEncoder().encode(userRegistrationRequest.getPassword()));
		userRepository.save(user);
	}
	
	 public UserDetailsServiceImpl() {
	        System.out.println("UserDetailsServiceImpl bean is being created.");
	    }

}
