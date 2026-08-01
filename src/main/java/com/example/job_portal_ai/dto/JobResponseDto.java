package com.example.job_portal_ai.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JobResponseDto {

    private Long id;

    private String title;

    private String description;

    private String requiredSkills;

    private String experience;

    private String salary;

    private String location;

    private String employmentType;

    private String recruiterName;

    private String recruiterEmail;
}