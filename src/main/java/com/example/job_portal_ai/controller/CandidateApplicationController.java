package com.example.job_portal_ai.controller;

import com.example.job_portal_ai.dto.JobApplicationRequestDto;
import com.example.job_portal_ai.dto.JobApplicationResponseDto;
import com.example.job_portal_ai.service.JobApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/candidate/applications")
@RequiredArgsConstructor
public class CandidateApplicationController {

    private final JobApplicationService jobApplicationService;


    @PostMapping
    public ResponseEntity<JobApplicationResponseDto> applyJob(
            @RequestBody JobApplicationRequestDto request
    ){

        JobApplicationResponseDto response =
                jobApplicationService.applyJob(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
