package org.example.backendstarter.features.auth.controllers;

import lombok.RequiredArgsConstructor;
import org.example.backendstarter.features.auth.dto.JwtResponse;
import org.example.backendstarter.features.auth.dto.LoginRequest;
import org.example.backendstarter.features.auth.services.AuthService;
import org.example.backendstarter.features.ums.dto.AUserDto;
import org.example.backendstarter.features.ums.services.AUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AUserService userService;


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest req) {
        String token = authService.login(req.username(), req.password());
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @GetMapping("/me")
    public ResponseEntity<AUserDto> getCurrentUser(Authentication auth) {
        String username = auth.getName();
        AUserDto profile = userService.getUserByUsername(username);
        return ResponseEntity.ok(profile);
    }
}
