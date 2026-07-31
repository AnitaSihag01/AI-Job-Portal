package com.example.job_portal_ai.service;

import com.example.job_portal_ai.dto.RecruiterProfileRequest;
import com.example.job_portal_ai.dto.RecruiterProfileResponse;
import com.example.job_portal_ai.entity.RecruiterProfile;
import com.example.job_portal_ai.entity.User;
import com.example.job_portal_ai.entity.type.Role;
import com.example.job_portal_ai.exception.RecruiterProfileNotFoundException;
import com.example.job_portal_ai.exception.UserIsNotRecruiterException;
import com.example.job_portal_ai.exception.UserNotFoundException;
import com.example.job_portal_ai.repository.RecruiterProfileRepository;
import com.example.job_portal_ai.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecruiterProfileService {

    private final RecruiterProfileRepository recruiterProfileRepository;
    private final UserRepository userRepository;


    public RecruiterProfileResponse createProfile(RecruiterProfileRequest request) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));


        if(user.getRole() != Role.RECRUITER){
            throw new UserIsNotRecruiterException(
                    "User is not recruiter"
            );
        }


        RecruiterProfile profile =
                recruiterProfileRepository.findByUser(user)
                        .orElse(
                                RecruiterProfile.builder()
                                        .companyName(request.getCompanyName())
                                        .companyDescription(request.getCompanyDescription())
                                        .website(request.getWebsite())
                                        .location(request.getLocation())
                                        .user(user)
                                        .build()
                        );


        RecruiterProfile savedProfile =
                recruiterProfileRepository.save(profile);


        return RecruiterProfileResponse.builder()
                .id(savedProfile.getId())
                .companyName(savedProfile.getCompanyName())
                .companyDescription(savedProfile.getCompanyDescription())
                .website(savedProfile.getWebsite())
                .location(savedProfile.getLocation())
                .recruiterName(user.getName())
                .email(user.getEmail())
                .build();
    }

    public RecruiterProfileResponse getProfile(){

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();


        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));


        RecruiterProfile profile =
                recruiterProfileRepository.findByUser(user)
                        .orElseThrow(() ->
                                new RecruiterProfileNotFoundException("Recruiter profile not found"));

        return RecruiterProfileResponse.builder()
                .id(profile.getId())
                .companyName(profile.getCompanyName())
                .companyDescription(profile.getCompanyDescription())
                .website(profile.getWebsite())
                .location(profile.getLocation())
                .recruiterName(user.getName())
                .email(user.getEmail())
                .build();
    }

    public void deleteProfile() {
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        RecruiterProfile profile = recruiterProfileRepository.findByUser(user)
                .orElseThrow(() ->
                        new RecruiterProfileNotFoundException("Recruiter profile not found"));

        recruiterProfileRepository.delete(profile);
    }
}