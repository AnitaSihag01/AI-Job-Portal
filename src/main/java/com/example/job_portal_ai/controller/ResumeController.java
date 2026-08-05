package com.example.job_portal_ai.controller;

import com.example.job_portal_ai.dto.ResumeAnalysisDto;
import com.example.job_portal_ai.service.impl.ResumeAnalysisService;
import com.example.job_portal_ai.service.impl.ResumeParserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/resume")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeParserService resumeParserService;
    private final ResumeAnalysisService resumeAnalysisService;

    @PostMapping("/upload")
    public ResponseEntity<ResumeAnalysisDto> uploadResume(
            @RequestParam("file") MultipartFile file
    ) {

        String resumeText = resumeParserService.extractText(file);

        ResumeAnalysisDto analysis =
                resumeAnalysisService.analyzeResume(
                        resumeText,
                        file.getOriginalFilename()
                );

        return ResponseEntity.ok(analysis);
    }
    @PostMapping("/analyze")
    public ResumeAnalysisDto analyze(@RequestBody String resumeText){
        return resumeAnalysisService.analyzeResume(
                resumeText,
                null
        );

       // return resumeAnalysisService.analyzeResume(resumeText);
    }

    @GetMapping("/my-analysis")
    public ResponseEntity<ResumeAnalysisDto> getMyAnalysis(){

        return ResponseEntity.ok(
                resumeAnalysisService.getMyAnalysis()
        );
    }
}