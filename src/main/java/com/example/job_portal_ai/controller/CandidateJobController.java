package com.example.job_portal_ai.controller;

import com.example.job_portal_ai.dto.JobResponseDto;
import com.example.job_portal_ai.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class CandidateJobController {

    private final JobService jobService;

    @GetMapping
    public ResponseEntity<List<JobResponseDto>> getAllJobs(){

        return ResponseEntity.ok(
                jobService.getAllJobs()
        );
    }

}
