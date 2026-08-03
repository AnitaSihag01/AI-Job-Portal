package com.example.job_portal_ai.impl;

import com.example.job_portal_ai.dto.ResumeAnalysisDto;

public interface ResumeAnalysisService {
    ResumeAnalysisDto analyzeResume(String resumeText);

}
