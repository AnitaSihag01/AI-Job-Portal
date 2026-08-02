package com.example.job_portal_ai.ai.impl;

import com.example.job_portal_ai.ai.AiService;
import org.springframework.stereotype.Service;

@Service
public class AiServiceImpl implements AiService {

    @Override
    public String analyzeResume(String resumeText) {

        return "Resume analyzed";
    }
}