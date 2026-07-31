package com.example.job_portal_ai.controller;

import com.example.job_portal_ai.dto.RecruiterProfileRequest;
import com.example.job_portal_ai.dto.RecruiterProfileResponse;
import com.example.job_portal_ai.dto.RegisterRequest;
import com.example.job_portal_ai.dto.RegisterResponse;
import com.example.job_portal_ai.entity.RecruiterProfile;
import com.example.job_portal_ai.service.AuthService;
import com.example.job_portal_ai.service.RecruiterProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<RecruiterProfileResponse> createProfile(
            @RequestBody RecruiterProfileRequest request){

        RecruiterProfileResponse response =
                recruiterProfileService.createProfile(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/profile")
    public ResponseEntity<RecruiterProfileResponse> getProfile(){

        RecruiterProfileResponse response =
                recruiterProfileService.getProfile();

        return ResponseEntity.ok(response);
    }

    // dashBoard

    @GetMapping("/dashboard")
    public ResponseEntity<RecruiterProfileResponse> dashboard() {
        return ResponseEntity.ok(recruiterProfileService.getProfile());
    }

    @DeleteMapping("/profile")
    public ResponseEntity<String> deleteProfile() {
        recruiterProfileService.deleteProfile();
        return ResponseEntity.ok("Recruiter profile deleted successfully.");

    }
}
