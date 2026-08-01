package com.example.job_portal_ai.dto;

import lombok.Data;

@Data
public class UpdateJobRequestDto {

    private String title;

    private String description;

    private String requiredSkills;

    private String experience;

    private String salary;

    private String location;

    private String employmentType;
}