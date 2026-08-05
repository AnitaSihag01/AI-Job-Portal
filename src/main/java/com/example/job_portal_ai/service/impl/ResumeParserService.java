package com.example.job_portal_ai.service.impl;

import org.springframework.web.multipart.MultipartFile;

public interface ResumeParserService {
    String extractText(MultipartFile file);
}
