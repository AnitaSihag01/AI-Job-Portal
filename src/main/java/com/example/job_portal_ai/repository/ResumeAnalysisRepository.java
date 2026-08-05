package com.example.job_portal_ai.repository;

import com.example.job_portal_ai.dto.ResumeAnalysisDto;
import com.example.job_portal_ai.entity.ResumeAnalysis;
import com.example.job_portal_ai.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResumeAnalysisRepository extends JpaRepository<ResumeAnalysis,Long> {
    Optional<ResumeAnalysis> findByUserId(Long userId);
    Optional<ResumeAnalysis> findByUser(User user);
}
