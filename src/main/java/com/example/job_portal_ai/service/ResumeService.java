package com.example.job_portal_ai.service;

import com.example.job_portal_ai.ai.service.AiService;
import com.example.job_portal_ai.entity.Resume;
import com.example.job_portal_ai.service.impl.ResumeParserService;
import com.example.job_portal_ai.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final ResumeParserService resumeParserService;
    private final AiService aiService;

    public Resume saveResume(Resume resume) {

        return resumeRepository.save(resume);
    }

    public Resume uploadResume(MultipartFile file) {
        String resumeText = resumeParserService.extractText(file);
        String analysis = aiService.analyzeResume(resumeText);
        System.out.println(analysis);

        try {

            String uploadDir = "uploads/";

            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(file.getOriginalFilename());

            Files.copy(
                    file.getInputStream(),
                    filePath,
                    StandardCopyOption.REPLACE_EXISTING
            );
            Resume resume = new Resume();
            resume.setFileName(file.getOriginalFilename());
            resume.setResumeText(resumeText);

            return resumeRepository.save(resume);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload resume", e);
        }
    }
}