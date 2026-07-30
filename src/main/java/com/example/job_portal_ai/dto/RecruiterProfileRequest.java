package com.example.job_portal_ai.dto;

import lombok.Data;

@Data
public class RecruiterProfileRequest {

    private String companyName;
    private String companyDescription;
    private String website;
    private String location;
    private Long userId;
}
