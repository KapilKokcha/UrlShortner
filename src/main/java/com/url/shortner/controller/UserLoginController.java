package com.url.shortner.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.url.shortner.security.JwtTokenProvider;
import com.url.shortner.user.UserLoginRequest;

@RestController
@RequestMapping("/api")
public class UserLoginController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
    private JwtTokenProvider jwtTokenProvider;

	@PostMapping("/login")
	public ResponseEntity<String> loginUser(@RequestBody UserLoginRequest userLoginRequest) {

		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				userLoginRequest.getUsername(), userLoginRequest.getPassword()));

		// Set the Authentication object in the SecurityContextHolder
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Now you can check if authentication is successful
		if (authentication.isAuthenticated()) {
			String token = jwtTokenProvider.generateToken(authentication);

		    // Authentication success logic here
		    return ResponseEntity.ok(token);
		} else {
		    // Authentication failed logic here
		    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
		}
	}
}
