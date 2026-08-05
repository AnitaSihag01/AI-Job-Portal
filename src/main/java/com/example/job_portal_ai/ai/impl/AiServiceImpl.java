package com.example.job_portal_ai.ai.impl;

import com.example.job_portal_ai.ai.service.AiService;
import org.springframework.stereotype.Service;

@Service
public class AiServiceImpl implements AiService {

    @Override
    public String analyzeResume(String resumeText) {

        return """
                Resume Analysis

                ATS Score: 75/100

                Skills Found:
                - Java
                - Spring Boot
                - PostgreSQL

                Suggestions:
                - Learn Docker
                - Learn AWS
                - Add JUnit testing
                - Build more REST API projects
                """;
    }
}