package com.example.job_portal_ai.controller;

import com.example.job_portal_ai.dto.CandidateProfileRequestDto;
import com.example.job_portal_ai.dto.CandidateProfileResponseDto;
import com.example.job_portal_ai.service.CandidateProfileService;
import com.example.job_portal_ai.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/candidate/profile")
@RequiredArgsConstructor
public class CandidateProfileController {


    private final CandidateProfileService candidateProfileService;
    private final FileStorageService fileStorageService;

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

    @GetMapping("/resume")
    public ResponseEntity<Resource> downloadResume() {

        String fileName = candidateProfileService.getResumeFileName();

        Resource resource = fileStorageService.loadFile(fileName);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + fileName + "\""
                )
                .body(resource);
    }
}