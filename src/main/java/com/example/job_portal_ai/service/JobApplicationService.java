package com.example.job_portal_ai.service;

import com.example.job_portal_ai.dto.JobApplicationRequestDto;
import com.example.job_portal_ai.dto.JobApplicationResponseDto;
import com.example.job_portal_ai.dto.RecruiterApplicationResponseDto;
import com.example.job_portal_ai.entity.Job;
import com.example.job_portal_ai.entity.JobApplication;
import com.example.job_portal_ai.entity.User;
import com.example.job_portal_ai.entity.type.ApplicationStatus;
import com.example.job_portal_ai.exception.UserNotFoundException;
import com.example.job_portal_ai.repository.JobApplicationRepository;
import com.example.job_portal_ai.repository.JobRepository;
import com.example.job_portal_ai.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.example.job_portal_ai.dto.ApplicationStatusRequestDto;
import com.example.job_portal_ai.entity.type.ApplicationStatus;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;  // ℹ️ save application
    private final JobRepository jobRepository;  // ℹ️ find the job candidate is applying for
    private final UserRepository userRepository; // ℹ️ find logged-in candidate

    public JobApplicationResponseDto applyJob(JobApplicationRequestDto request){

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();


        User candidate = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("Candidate not found")
                );


        Job job = jobRepository.findById(request.getJobId())
                .orElseThrow(() ->
                        new RuntimeException("Job not found")
                );


        JobApplication application = JobApplication.builder()
                .candidate(candidate)
                .job(job)
                .status(ApplicationStatus.APPLIED)
                .appliedAt(LocalDateTime.now())
                .build();


        JobApplication savedApplication =
                jobApplicationRepository.save(application);


        return JobApplicationResponseDto.builder()
                .id(savedApplication.getId())
                .jobId(job.getId())
                .jobTitle(job.getTitle())
                .candidateName(candidate.getName())
                .candidateEmail(candidate.getEmail())
                .status(savedApplication.getStatus().name())
                .appliedAt(savedApplication.getAppliedAt())
                .build();
    }

    public List<RecruiterApplicationResponseDto> getRecruiterApplications(){

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();


        User recruiter = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("Recruiter not found")
                );


        List<JobApplication> applications =
                jobApplicationRepository.findByJobRecruiter(recruiter);


        return applications.stream()
                .map(application ->
                        RecruiterApplicationResponseDto.builder()
                                .applicationId(application.getId())
                                .jobTitle(application.getJob().getTitle())
                                .candidateName(application.getCandidate().getName())
                                .candidateEmail(application.getCandidate().getEmail())
                                .status(application.getStatus().name())
                                .build()
                )
                .toList();

        // Logic:
        //Get logged-in recruiter email from JWT.
        //Find recruiter user.
        //Find all applications for recruiter's jobs.
        //Convert them into safe response DTO.
    }

    public RecruiterApplicationResponseDto updateApplicationStatus(
            Long applicationId,
            ApplicationStatusRequestDto request
    ) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User recruiter = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("Recruiter not found")
                );

        JobApplication application = jobApplicationRepository
                .findById(applicationId)
                .orElseThrow(() ->
                        new RuntimeException("Application not found")
                );

        if (!application.getJob().getRecruiter().getId().equals(recruiter.getId())) {
            throw new RuntimeException(
                    "You are not authorized to update this application."
            );
        }

        application.setStatus(
                ApplicationStatus.valueOf(request.getStatus().toUpperCase())
        );

        JobApplication updatedApplication =
                jobApplicationRepository.save(application);

        return RecruiterApplicationResponseDto.builder()
                .applicationId(updatedApplication.getId())
                .jobTitle(updatedApplication.getJob().getTitle())
                .candidateName(updatedApplication.getCandidate().getName())
                .candidateEmail(updatedApplication.getCandidate().getEmail())
                .status(updatedApplication.getStatus().name())
                .build();

        // what this method do ->

        //Gets the logged-in recruiter from the JWT.
        //Finds the application by its ID.
        //Checks that the logged-in recruiter actually owns the job. This prevents one recruiter from changing another recruiter's applications.
        //Updates the status (SHORTLISTED, REJECTED, or HIRED).
        //Saves the change and returns the updated response.
    }
}
