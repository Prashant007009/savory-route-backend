package com.savory_route.auth_service.service;

import com.savory_route.auth_service.dto.JwtResponse;
import com.savory_route.auth_service.dto.SignupRequest;
import com.savory_route.auth_service.usermodel.AuthModel;
import com.savory_route.auth_service.dto.LoginRequest;

public interface AuthService {
	void Signup(SignupRequest request);
	JwtResponse login(LoginRequest request);
	AuthModel getUser(String id);
}
