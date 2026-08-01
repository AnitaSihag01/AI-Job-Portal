package com.example.job_portal_ai.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CandidateProfileRequestDto {

    private String phone;

    private String address;

    private String skills;

    private String experience;

    private String education;

    private String githubUrl;

    private String linkedinUrl;
}