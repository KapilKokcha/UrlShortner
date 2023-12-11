package com.url.shortner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.url.shortner.user.UserDetailsServiceImpl;
import com.url.shortner.user.UserRegistrationRequest;

@RestController
@RequestMapping("/api")
public class RegistrationController {

	@Autowired
	public UserDetailsServiceImpl userDetailsServiceImpl;

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody UserRegistrationRequest userRegistrationRequest) {
		userDetailsServiceImpl.registerUser(userRegistrationRequest);
		return ResponseEntity.ok("Registration successful");
	}
}
