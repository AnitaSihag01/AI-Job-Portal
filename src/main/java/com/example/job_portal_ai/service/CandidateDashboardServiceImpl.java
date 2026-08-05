package com.example.job_portal_ai.service;

import com.example.job_portal_ai.dto.CandidateDashboardDto;
import com.example.job_portal_ai.entity.ResumeAnalysis;
import com.example.job_portal_ai.entity.User;
import com.example.job_portal_ai.repository.ResumeAnalysisRepository;
import com.example.job_portal_ai.repository.UserRepository;
import com.example.job_portal_ai.service.impl.CandidateDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CandidateDashboardServiceImpl implements CandidateDashboardService {


    private final UserRepository userRepository;
    private final ResumeAnalysisRepository resumeAnalysisRepository;


    @Override
    public CandidateDashboardDto getDashboard() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();


        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));


        ResumeAnalysis analysis = resumeAnalysisRepository
                .findByUser(user)
                .orElse(null);


        CandidateDashboardDto dto = new CandidateDashboardDto();

        dto.setName(user.getName());
        dto.setEmail(user.getEmail());


        if(analysis != null){

            dto.setResumeUploaded(true);
            dto.setUploadedAt(analysis.getUploadedAt());

            dto.setSkills(analysis.getSkills());
            dto.setEducation(analysis.getEducation());
            dto.setProjects(analysis.getProjects());

            dto.setExperience(analysis.getExperience());
            dto.setGithubUrl(analysis.getGithubUrl());

        }
        else {

            dto.setResumeUploaded(false);
        }


        return dto;
    }
}