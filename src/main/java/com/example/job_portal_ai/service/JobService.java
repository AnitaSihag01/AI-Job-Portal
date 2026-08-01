package com.example.job_portal_ai.service;

import com.example.job_portal_ai.dto.JobRequestDto;
import com.example.job_portal_ai.dto.JobResponseDto;
import com.example.job_portal_ai.dto.UpdateJobRequestDto;
import com.example.job_portal_ai.entity.Job;
import com.example.job_portal_ai.entity.User;
import com.example.job_portal_ai.entity.type.Role;
import com.example.job_portal_ai.exception.CannotDeleteJobException;
import com.example.job_portal_ai.exception.JobNotFoundException;
import com.example.job_portal_ai.exception.UserIsNotRecruiterException;
import com.example.job_portal_ai.exception.UserNotFoundException;
import com.example.job_portal_ai.repository.JobRepository;
import com.example.job_portal_ai.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository; // jobs -> database
    private final UserRepository userRepository; // used to find logged-in recruiter


    public JobResponseDto createJob(JobRequestDto request) {


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


        Job savedJob = jobRepository.save(job);

        return JobResponseDto.builder()
                .id(savedJob.getId())
                .title(savedJob.getTitle())
                .description(savedJob.getDescription())
                .requiredSkills(savedJob.getRequiredSkills())
                .experience(savedJob.getExperience())
                .salary(savedJob.getSalary())
                .location(savedJob.getLocation())
                .employmentType(savedJob.getEmploymentType())
                .recruiterName(recruiter.getName())
                .recruiterEmail(recruiter.getEmail())
                .build();
    }

    public List<JobResponseDto> getMyJobs(){

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();


        User recruiter = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("Recruiter not found"));


        List<Job> jobs = jobRepository.findByRecruiter(recruiter);


        return jobs.stream()
                .map(job -> JobResponseDto.builder()
                        .id(job.getId())
                        .title(job.getTitle())
                        .description(job.getDescription())
                        .requiredSkills(job.getRequiredSkills())
                        .experience(job.getExperience())
                        .salary(job.getSalary())
                        .location(job.getLocation())
                        .employmentType(job.getEmploymentType())
                        .recruiterName(recruiter.getName())
                        .recruiterEmail(recruiter.getEmail())
                        .build()
                )
                .toList();
    }

    public JobResponseDto updateJob(Long jobId, UpdateJobRequestDto request){

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();


        User recruiter = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("Recruiter not found"));


        Job job = jobRepository.findById(jobId)
                .orElseThrow(() ->
                        new RuntimeException("Job not found"));


        // ownership(Recruiter) check
        if(!job.getRecruiter().getId().equals(recruiter.getId())){  //  ⚠️⚠️ this is the security check
            throw new RuntimeException("You cannot update this job");
        }


        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setRequiredSkills(request.getRequiredSkills());
        job.setExperience(request.getExperience());
        job.setSalary(request.getSalary());
        job.setLocation(request.getLocation());
        job.setEmploymentType(request.getEmploymentType());


        Job updatedJob = jobRepository.save(job);


        return JobResponseDto.builder()
                .id(updatedJob.getId())
                .title(updatedJob.getTitle())
                .description(updatedJob.getDescription())
                .requiredSkills(updatedJob.getRequiredSkills())
                .experience(updatedJob.getExperience())
                .salary(updatedJob.getSalary())
                .location(updatedJob.getLocation())
                .employmentType(updatedJob.getEmploymentType())
                .recruiterName(recruiter.getName())
                .recruiterEmail(recruiter.getEmail())
                .build();
    }

    public void deleteJob(Long jobId){

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();


        Job job = jobRepository.findById(jobId)
                .orElseThrow(() ->
                        new JobNotFoundException("Job not found")
                );


        if(!job.getRecruiter().getEmail().equals(email)){
            throw new CannotDeleteJobException("You cannot delete this job");
        }


        jobRepository.delete(job);
    }

    public List<JobResponseDto> getAllJobs(){

        List<Job> jobs = jobRepository.findAll();

        return jobs.stream()
                .map(job -> JobResponseDto.builder()
                        .id(job.getId())
                        .title(job.getTitle())
                        .description(job.getDescription())
                        .requiredSkills(job.getRequiredSkills())
                        .experience(job.getExperience())
                        .salary(job.getSalary())
                        .location(job.getLocation())
                        .employmentType(job.getEmploymentType())
                        .recruiterName(job.getRecruiter().getName())
                        .recruiterEmail(job.getRecruiter().getEmail())
                        .build()
                )
                .toList();
    }
}
