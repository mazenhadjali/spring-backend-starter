package org.example.backendstarter.auth.controllers;

import lombok.RequiredArgsConstructor;
import org.example.backendstarter.auth.dto.JwtResponse;
import org.example.backendstarter.auth.dto.LoginRequest;
import org.example.backendstarter.auth.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest req) {
        String token = authService.login(req.username(), req.password());
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
