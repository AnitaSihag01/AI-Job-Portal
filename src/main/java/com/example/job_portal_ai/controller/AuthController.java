package com.example.job_portal_ai.controller;

import com.example.job_portal_ai.dto.LoginRequestDto;
import com.example.job_portal_ai.dto.LoginResponseDto;
import com.example.job_portal_ai.dto.RegisterRequest;
import com.example.job_portal_ai.dto.RegisterResponse;
import com.example.job_portal_ai.entity.User;
import com.example.job_portal_ai.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto login){
        LoginResponseDto response = authService.login(login);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public String test() {
        return "JWT Authentication Successful";
    }
}
