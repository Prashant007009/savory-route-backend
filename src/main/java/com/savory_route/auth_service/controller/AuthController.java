package com.savory_route.auth_service.controller;

import com.savory_route.auth_service.dto.*;
import com.savory_route.auth_service.service.AuthService;
import com.savory_route.auth_service.usermodel.AuthModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService userService;
    
    @Autowired
    public AuthController(AuthService userService) {
    	this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest request) {
        userService.Signup(request);
        return ResponseEntity.ok("User registered");
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }
    
    @GetMapping("/user")
    public ResponseEntity<AuthModel> user(@RequestHeader("Authorization") String id){
		return ResponseEntity.ok(userService.getUser(id));	
    }
}
