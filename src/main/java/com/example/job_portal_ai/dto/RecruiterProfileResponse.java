package com.example.job_portal_ai.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecruiterProfileResponse {

    private Long id;
    private String companyName;

    private String companyDescription;

    private String website;

    private String location;

    private String recruiterName;

    private String email;
}
