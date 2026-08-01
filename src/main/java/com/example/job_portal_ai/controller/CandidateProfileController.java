package com.example.job_portal_ai.controller;

import com.example.job_portal_ai.dto.CandidateProfileRequestDto;
import com.example.job_portal_ai.dto.CandidateProfileResponseDto;
import com.example.job_portal_ai.service.CandidateProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/candidate/profile")
@RequiredArgsConstructor
public class CandidateProfileController {


    private final CandidateProfileService candidateProfileService;


    @PostMapping
    public ResponseEntity<CandidateProfileResponseDto> createProfile(
            @RequestBody CandidateProfileRequestDto request
    ){

        return ResponseEntity.ok(
                candidateProfileService.createProfile(request)
        );
    }


    @GetMapping
    public ResponseEntity<CandidateProfileResponseDto> getProfile(){

        return ResponseEntity.ok(
                candidateProfileService.getProfile()
        );
    }


    @PutMapping
    public ResponseEntity<CandidateProfileResponseDto> updateProfile(
            @RequestBody CandidateProfileRequestDto request
    ){

        return ResponseEntity.ok(
                candidateProfileService.updateProfile(request)
        );
    }


    @DeleteMapping
    public ResponseEntity<String> deleteProfile(){

        candidateProfileService.deleteProfile();

        return ResponseEntity.ok(
                "Candidate profile deleted successfully."
        );
    }

    @PostMapping("/resume")
    public ResponseEntity<CandidateProfileResponseDto> uploadResume(
            @RequestParam("file") MultipartFile file
    ){

        return ResponseEntity.ok(
                candidateProfileService.uploadResume(file)
        );
    }
}