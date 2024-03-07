package com.springboot.blog.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.entity.Role;
import com.springboot.blog.entity.User;
import com.springboot.blog.payload.JWTAuthResponse;
import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.payload.SignUpDto;
import com.springboot.blog.repository.RoleRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.security.JwtTokenProvider;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name="AUTH CONTROLLER" ,description ="AUTH CONTROLLER EXPOSES SIGNIN AND SIGNUP REST API")
@RestController
@RequestMapping("/api/v1/auth")
@SecurityRequirement(name = "Bear Authentication")
public class AuthController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	
	@Operation(summary  = "REST API TO SIGNIN OR LOGIN USER TO BLOG APPLICATION")
	@PostMapping("/signin")
	public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto){
		
		
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				loginDto.getUsernameOrEmail(), loginDto.getPassword()));
//		
//	SecurityContextHolder.getContext().setAuthentication(authentication);
//	
//	//get token from tokenprovider
//	
		String token = jwtTokenProvider.generateToken(authentication);
//
//    return ResponseEntity.ok(new JWTAuthResponse(token, token ));
		
		  JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
	        jwtAuthResponse.setAccessToken(token);

	        return ResponseEntity.ok(jwtAuthResponse);
	
	
	}
	
	@Operation(summary  = "REST API TO REGISTER OR SIGNUP USER TO BLOG APPLICATION")
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){
		
		//check if username exists in db
		
		if(userRepository.existsByUsername(signUpDto.getUsername())) {
			
			return new ResponseEntity<>("username is already taken!" ,HttpStatus.BAD_REQUEST);
			
		}
		
		//check if email exists in db
		
		if(userRepository.existsByEmail(signUpDto.getEmail())) {
			
			return new ResponseEntity<>("email already taken",HttpStatus.OK);
		}
		
		User user = new User();
		
		user.setName(signUpDto.getName());
		user.setUsername(signUpDto.getUsername());
		user.setEmail(signUpDto.getEmail());
		user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
		
		Role roles = roleRepository.findByName("ROLE_ADMIN").get();
		user.setRoles(Collections.singleton(roles));
		
		userRepository.save(user);
		
		return new ResponseEntity<>("user registered successfully" ,HttpStatus.OK);
	}

}
