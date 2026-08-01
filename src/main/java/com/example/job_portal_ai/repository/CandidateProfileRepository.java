package com.example.job_portal_ai.repository;


import com.example.job_portal_ai.entity.CandidateProfile;
import com.example.job_portal_ai.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CandidateProfileRepository extends JpaRepository<CandidateProfile,Long> {
    Optional<CandidateProfile> findByUser(User user); // why ->  after JWT authentication we get the logged-in user's email
}
