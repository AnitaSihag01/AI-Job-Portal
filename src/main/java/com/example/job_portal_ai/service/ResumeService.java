package com.example.job_portal_ai.service;

import com.example.job_portal_ai.entity.Resume;
import com.example.job_portal_ai.repository.ResumeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ResumeService {

    private final ResumeRepository resumeRepository;

    public ResumeService(ResumeRepository resumeRepository) {
        this.resumeRepository = resumeRepository;
    }

    public Resume saveResume(Resume resume) {

        return resumeRepository.save(resume);
    }

    public Resume uploadResume(MultipartFile file) {

        try {

            String uploadDir = "uploads/";

            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(file.getOriginalFilename());

            Files.copy(file.getInputStream(), filePath);

            Resume resume = new Resume();
            resume.setFileName(file.getOriginalFilename());

            return resumeRepository.save(resume);

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload resume", e);
        }
    }
}