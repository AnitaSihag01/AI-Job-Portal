package com.example.job_portal_ai.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CandidateProfileResponseDto {

    private Long id;

    private String candidateName;

    private String email;

    private String phone;

    private String address;

    private String skills;

    private String experience;

    private String education;

    private String githubUrl;

    private String linkedinUrl;

    private String resumeUrl;
}