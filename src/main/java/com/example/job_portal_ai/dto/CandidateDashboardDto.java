package com.example.job_portal_ai.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CandidateDashboardDto {

    private String name;

    private String email;

    private boolean resumeUploaded;

    private LocalDateTime uploadedAt;

    private List<String> skills;

    private List<String> education;

    private List<String> projects;

    private String experience;

    private String githubUrl;
}