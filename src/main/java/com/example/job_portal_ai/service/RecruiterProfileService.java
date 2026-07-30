package com.example.job_portal_ai.service;

import com.example.job_portal_ai.dto.RecruiterProfileRequest;
import com.example.job_portal_ai.entity.RecruiterProfile;
import com.example.job_portal_ai.entity.User;
import com.example.job_portal_ai.entity.type.Role;
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


    public RecruiterProfile createProfile(RecruiterProfileRequest request) {


        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        System.out.println("EMAIL FROM JWT: " + email);
        User user = userRepository.findByEmail(email)
                .orElseThrow( () ->
                        new UserNotFoundException("User not found"));


        if(user.getRole() != Role.RECRUITER){
            throw new UserIsNotRecruiterException(
                    "User is not a recruiter"
            );
        }


        RecruiterProfile profile = RecruiterProfile.builder()
                .companyName(request.getCompanyName())
                .companyDescription(request.getCompanyDescription())
                .website(request.getWebsite())
                .location(request.getLocation())
                .user(user)
                .build();


        return recruiterProfileRepository.save(profile);
    }
}