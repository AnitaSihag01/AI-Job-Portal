package com.example.job_portal_ai.impl;

import org.springframework.web.multipart.MultipartFile;

public interface ResumeParserService {
    String extractText(MultipartFile file);
}
