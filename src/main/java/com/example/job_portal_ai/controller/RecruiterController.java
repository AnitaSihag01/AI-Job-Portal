package com.example.job_portal_ai.controller;

import com.example.job_portal_ai.dto.RecruiterProfileRequest;
import com.example.job_portal_ai.dto.RegisterRequest;
import com.example.job_portal_ai.dto.RegisterResponse;
import com.example.job_portal_ai.entity.RecruiterProfile;
import com.example.job_portal_ai.service.AuthService;
import com.example.job_portal_ai.service.RecruiterProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/recruiter")
@RequiredArgsConstructor
public class RecruiterController {

    private final AuthService authService;
    private final RecruiterProfileService recruiterProfileService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @RequestBody RegisterRequest request){

        RegisterResponse response =
                authService.registerRecruiter(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/profile")
    public ResponseEntity<RecruiterProfile> createProfile(
            @RequestBody RecruiterProfileRequest request){

        RecruiterProfile profile =
                recruiterProfileService.createProfile(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(profile);
    }
}
