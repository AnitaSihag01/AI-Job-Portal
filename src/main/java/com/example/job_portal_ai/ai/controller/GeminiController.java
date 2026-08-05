package com.example.job_portal_ai.ai.controller;

import com.example.job_portal_ai.ai.service.AiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
public class GeminiController {

    private final AiService aiService;

    public GeminiController(AiService aiService) {
        this.aiService = aiService;
    }

    @GetMapping("/test")
    public String test() {
        return aiService.analyzeResume("Test resume");
    }
}