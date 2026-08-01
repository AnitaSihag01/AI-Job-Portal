package com.example.job_portal_ai.controller;

import com.example.job_portal_ai.dto.JobRequestDto;
import com.example.job_portal_ai.dto.JobResponseDto;
import com.example.job_portal_ai.dto.UpdateJobRequestDto;
import com.example.job_portal_ai.repository.JobRepository;
import com.example.job_portal_ai.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recruiter")
@RequiredArgsConstructor
public class RecruiterJobController {

    private final JobRepository jobRepository;
    private final JobService jobService;

    @PostMapping("/post")
    public ResponseEntity<JobResponseDto> createJob(
            @RequestBody JobRequestDto request){

        JobResponseDto jobResponseDto = jobService.createJob(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(jobResponseDto);
    }

    @GetMapping("/jobs")
    public ResponseEntity<List<JobResponseDto>> getMyJobs(){

        List<JobResponseDto> jobs = jobService.getMyJobs();

        return ResponseEntity.ok(jobs);
    }

    @PutMapping("/jobs/{jobId}")
    public ResponseEntity<JobResponseDto> updateJob(
            @PathVariable Long jobId,
            @RequestBody UpdateJobRequestDto request){

        JobResponseDto response = jobService.updateJob(jobId, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{jobId}")
    public ResponseEntity<String> deleteJob(
            @PathVariable Long jobId
    ){
        jobService.deleteJob(jobId);

        return ResponseEntity.ok(
                "Job deleted successfully"
        );
    }
}

