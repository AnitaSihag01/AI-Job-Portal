package com.example.job_portal_ai.ai;

import org.springframework.web.bind.annotation.*;

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