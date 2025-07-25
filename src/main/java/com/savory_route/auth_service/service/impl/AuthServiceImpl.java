package com.savory_route.auth_service.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.savory_route.auth_service.dto.JwtResponse;
import com.savory_route.auth_service.dto.LoginRequest;
import com.savory_route.auth_service.dto.SignupRequest;
import com.savory_route.auth_service.service.AuthService;
import com.savory_route.auth_service.userdao.AuthDao;
import com.savory_route.auth_service.usermodel.AuthModel;
import com.savory_route.auth_service.util.JwtUtil;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthServiceImpl(AuthDao userDao, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void Signup(SignupRequest request) {
    	if (request.getPhoneNumber() == 0) {
            throw new RuntimeException("Phone number cannot be 0");
        }

        if (userDao.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        if (userDao.findByPhoneNumber(request.getPhoneNumber()).isPresent()) {
            throw new RuntimeException("Phone number already exists");
        }

        // Split full name to first and last
        String[] parts = request.getName().split(" ", 2);
        String first = parts[0];
        String last = parts.length > 1 ? parts[1] : "";

        AuthModel user = AuthModel.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(first)
                .lastName(last)
                .phoneNumber(request.getPhoneNumber())
                .build();

        userDao.save(user);
    }

    @Override
    public JwtResponse login(LoginRequest request) {
    	long time = System.currentTimeMillis();
    	String identifier = request.getIdentifier();
        Optional<AuthModel> userOpt;

        if (identifier.contains("@")) {
            userOpt = userDao.findByEmail(identifier);
        } else {
            try {
                long phone = Long.parseLong(identifier);
                userOpt = userDao.findByPhoneNumber(phone);
            } catch (NumberFormatException e) {
                throw new RuntimeException("Invalid phone number");
            }
        }

        AuthModel user = userOpt.orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user);
        System.out.println(System.currentTimeMillis()-time);        
        return new JwtResponse(token);  
    }

    @Override
    public AuthModel getUser(String id) {
    	if (id == null || !id.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid or missing Authorization header");
        }

        String token = id.substring(7); // Strip "Bearer "
        String userId = jwtUtil.validateAndGetUserId(token);
        Optional<AuthModel> userDetail = userDao.findById(userId);
        return userDetail.orElse(null);
    }
}
