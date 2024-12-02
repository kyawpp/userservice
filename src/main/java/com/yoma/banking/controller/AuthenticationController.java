package com.yoma.banking.controller;

import com.yoma.banking.dto.AuthenticationRequest;
import com.yoma.banking.dto.AuthenticationResponse;
import com.yoma.banking.service.UserService;
import com.yoma.banking.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, UserService userService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
            );
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        // Assuming user has a role "USER"
        List<String> roles = Collections.singletonList("USER");
        String accessToken = jwtUtil.generateToken(authenticationRequest.getEmail(), roles);
        String refreshToken = jwtUtil.generateRefreshToken(authenticationRequest.getEmail());

        AuthenticationResponse response = new AuthenticationResponse(accessToken, refreshToken);
        return ResponseEntity.ok(response);
    }

}
