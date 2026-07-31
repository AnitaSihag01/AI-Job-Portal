package com.example.job_portal_ai.service;

import com.example.job_portal_ai.dto.JobRequest;
import com.example.job_portal_ai.entity.Job;
import com.example.job_portal_ai.entity.User;
import com.example.job_portal_ai.entity.type.Role;
import com.example.job_portal_ai.exception.UserIsNotRecruiterException;
import com.example.job_portal_ai.exception.UserNotFoundException;
import com.example.job_portal_ai.repository.JobRepository;
import com.example.job_portal_ai.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository; // jobs -> database
    private final UserRepository userRepository; // used to find logged-in recruiter


    public Job createJob(JobRequest request) {


        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();


        User recruiter = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("Recruiter not found"));


        if(recruiter.getRole() != Role.RECRUITER){
            throw new UserIsNotRecruiterException(
                    "User is not recruiter"
            );
        }


        Job job = Job.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .requiredSkills(request.getRequiredSkills())
                .experience(request.getExperience())
                .salary(request.getSalary())
                .location(request.getLocation())
                .employmentType(request.getEmploymentType())
                .recruiter(recruiter)
                .build();


        return jobRepository.save(job);
    }
}
