package com.example.job_portal_ai.controller;

import com.example.job_portal_ai.dto.ApplicationStatusRequestDto;
import com.example.job_portal_ai.dto.RecruiterApplicationResponseDto;
import com.example.job_portal_ai.service.JobApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/recruiter/applications")
@RequiredArgsConstructor
public class RecruiterApplicationController {

    private final JobApplicationService jobApplicationService;


    @GetMapping
    public ResponseEntity<List<RecruiterApplicationResponseDto>> getApplications(){

        return ResponseEntity.ok(
                jobApplicationService.getRecruiterApplications()
        );
    }

    @PutMapping("/{applicationId}/status")
    public ResponseEntity<RecruiterApplicationResponseDto> updateApplicationStatus(
            @PathVariable Long applicationId,
            @RequestBody ApplicationStatusRequestDto request
    ) {

        RecruiterApplicationResponseDto response =
                jobApplicationService.updateApplicationStatus(applicationId, request);

        return ResponseEntity.ok(response);
    }
}
