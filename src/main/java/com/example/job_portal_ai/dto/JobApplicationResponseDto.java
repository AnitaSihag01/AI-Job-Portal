package com.example.job_portal_ai.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class JobApplicationResponseDto {

    private Long id;

    private Long jobId;

    private String jobTitle;

    private String candidateName;

    private String candidateEmail;

    private String status;

    private LocalDateTime appliedAt;
}
