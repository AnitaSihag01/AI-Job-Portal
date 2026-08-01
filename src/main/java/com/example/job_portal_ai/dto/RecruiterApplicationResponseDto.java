package com.example.job_portal_ai.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RecruiterApplicationResponseDto {

    private Long applicationId;

    private String jobTitle;

    private String candidateName;

    private String candidateEmail;

    private String status;
}
// separate dto because recruiter doesn't need full objects ,such as : pwd,user entity, job entity details
