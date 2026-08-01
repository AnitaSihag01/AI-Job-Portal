package com.example.job_portal_ai.repository;

import com.example.job_portal_ai.entity.JobApplication;
import com.example.job_portal_ai.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication,Long> {

    List<JobApplication> findByJobRecruiter(User recruiter); // find all the jobs belongs to this recruiter
}
