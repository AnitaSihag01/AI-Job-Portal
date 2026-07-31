package com.example.job_portal_ai.repository;

import com.example.job_portal_ai.entity.RecruiterProfile;
import com.example.job_portal_ai.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecruiterProfileRepository extends JpaRepository<RecruiterProfile,Long> {
    boolean existsByUser(User user);
    Optional<RecruiterProfile> findByUser(User user);
}