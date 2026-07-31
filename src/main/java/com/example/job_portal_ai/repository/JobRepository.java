package com.example.job_portal_ai.repository;

import com.example.job_portal_ai.entity.Job;
import com.example.job_portal_ai.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface JobRepository extends JpaRepository<Job,Long> {

    List<Job> findByRecruiter(User recruiter);


}
