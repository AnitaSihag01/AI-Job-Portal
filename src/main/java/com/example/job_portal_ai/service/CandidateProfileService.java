package com.example.job_portal_ai.service;

import com.example.job_portal_ai.dto.CandidateProfileRequestDto;
import com.example.job_portal_ai.dto.CandidateProfileResponseDto;
import com.example.job_portal_ai.entity.CandidateProfile;
import com.example.job_portal_ai.entity.User;
import com.example.job_portal_ai.exception.UserNotFoundException;
import com.example.job_portal_ai.repository.CandidateProfileRepository;
import com.example.job_portal_ai.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CandidateProfileService {

    private final CandidateProfileRepository candidateProfileRepository;
    private final UserRepository userRepository; // why -> need the logged-in candidate:
    private final FileStorageService fileStorageService;


    private User getCurrentUser() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found")
                );
    }


    public CandidateProfileResponseDto createProfile(
            CandidateProfileRequestDto request
    ) {

        User user = getCurrentUser();

        CandidateProfile profile =
                candidateProfileRepository.findByUser(user)
                        .orElse(
                                CandidateProfile.builder()
                                        .user(user)
                                        .build()
                        );


        profile.setPhone(request.getPhone());
        profile.setAddress(request.getAddress());
        profile.setSkills(request.getSkills());
        profile.setExperience(request.getExperience());
        profile.setEducation(request.getEducation());
        profile.setGithubUrl(request.getGithubUrl());
        profile.setLinkedinUrl(request.getLinkedinUrl());

        CandidateProfile savedProfile =
                candidateProfileRepository.save(profile);


        return CandidateProfileResponseDto.builder()
                .id(savedProfile.getId())
                .candidateName(user.getName())
                .email(user.getEmail())
                .phone(savedProfile.getPhone())
                .address(savedProfile.getAddress())
                .skills(savedProfile.getSkills())
                .experience(savedProfile.getExperience())
                .education(savedProfile.getEducation())
                .githubUrl(savedProfile.getGithubUrl())
                .linkedinUrl(savedProfile.getLinkedinUrl())
                .resumeUrl(savedProfile.getResumeUrl())
                .resumeUploaded(savedProfile.isResumeUploaded())
                .uploadedAt(savedProfile.getUploadedAt())
                .build();

        // Logic:
        //Get logged-in candidate from JWT.
        //Check if profile already exists.
        //If not, create a new profile.
        //Fill profile details.
        //Save it.
        //Return clean response DTO.
    }

    public CandidateProfileResponseDto getProfile(){

        User user = getCurrentUser();


        CandidateProfile profile =
                candidateProfileRepository.findByUser(user)
                        .orElseThrow(() ->
                                new RuntimeException("Profile not found")
                        );


        return CandidateProfileResponseDto.builder()
                .id(profile.getId())
                .candidateName(user.getName())
                .email(user.getEmail())
                .phone(profile.getPhone())
                .address(profile.getAddress())
                .skills(profile.getSkills())
                .experience(profile.getExperience())
                .education(profile.getEducation())
                .githubUrl(profile.getGithubUrl())
                .linkedinUrl(profile.getLinkedinUrl())
                .resumeUrl(profile.getResumeUrl())
                .resumeUploaded(profile.isResumeUploaded())
                .uploadedAt(profile.getUploadedAt())
                .build();
    }

    public CandidateProfileResponseDto updateProfile(
            CandidateProfileRequestDto request
    ) {

        User user = getCurrentUser();


        CandidateProfile profile =
                candidateProfileRepository.findByUser(user)
                        .orElseThrow(() ->
                                new RuntimeException("Profile not found")
                        );


        profile.setPhone(request.getPhone());
        profile.setAddress(request.getAddress());
        profile.setSkills(request.getSkills());
        profile.setExperience(request.getExperience());
        profile.setEducation(request.getEducation());
        profile.setGithubUrl(request.getGithubUrl());
        profile.setLinkedinUrl(request.getLinkedinUrl());


        CandidateProfile updatedProfile =
                candidateProfileRepository.save(profile);


        return CandidateProfileResponseDto.builder()
                .id(updatedProfile.getId())
                .candidateName(user.getName())
                .email(user.getEmail())
                .phone(updatedProfile.getPhone())
                .address(updatedProfile.getAddress())
                .skills(updatedProfile.getSkills())
                .experience(updatedProfile.getExperience())
                .education(updatedProfile.getEducation())
                .githubUrl(updatedProfile.getGithubUrl())
                .linkedinUrl(updatedProfile.getLinkedinUrl())
                .resumeUrl(updatedProfile.getResumeUrl())
                .resumeUploaded(updatedProfile.isResumeUploaded())
                .uploadedAt(updatedProfile.getUploadedAt())
                .build();
    }
    public void deleteProfile(){

        User user = getCurrentUser();


        CandidateProfile profile =
                candidateProfileRepository.findByUser(user)
                        .orElseThrow(() ->
                                new RuntimeException("Profile not found")
                        );


        candidateProfileRepository.delete(profile);
    }

    // Add resume Method
    public CandidateProfileResponseDto uploadResume(
            MultipartFile file
    ) {

        User user = getCurrentUser();


        CandidateProfile profile =
                candidateProfileRepository.findByUser(user)
                        .orElseThrow(() ->
                                new RuntimeException("Profile not found")
                        );


        String fileName =
                fileStorageService.saveFile(file);


        profile.setResumeUrl(fileName);
        profile.setResumeUploaded(true);
        profile.setUploadedAt(LocalDateTime.now());


        CandidateProfile savedProfile =
                candidateProfileRepository.save(profile);


        return CandidateProfileResponseDto.builder()
                .id(savedProfile.getId())
                .candidateName(user.getName())
                .email(user.getEmail())
                .phone(savedProfile.getPhone())
                .address(savedProfile.getAddress())
                .skills(savedProfile.getSkills())
                .experience(savedProfile.getExperience())
                .education(savedProfile.getEducation())
                .githubUrl(savedProfile.getGithubUrl())
                .linkedinUrl(savedProfile.getLinkedinUrl())
                .resumeUrl(savedProfile.getResumeUrl())
                .resumeUploaded(savedProfile.isResumeUploaded())
                .uploadedAt(savedProfile.getUploadedAt())
                .build();
    }

    public String getResumeFileName() {

        User user = getCurrentUser();

        CandidateProfile profile = candidateProfileRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException("Profile not found")
                );

        return profile.getResumeUrl();
    }
}
