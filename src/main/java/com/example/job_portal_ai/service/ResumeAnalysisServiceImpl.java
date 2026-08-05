package com.example.job_portal_ai.service;


import com.example.job_portal_ai.dto.ResumeAnalysisDto;
import com.example.job_portal_ai.entity.CandidateProfile;
import com.example.job_portal_ai.entity.ResumeAnalysis;
import com.example.job_portal_ai.entity.User;
import com.example.job_portal_ai.repository.CandidateProfileRepository;
import com.example.job_portal_ai.repository.ResumeAnalysisRepository;
import com.example.job_portal_ai.repository.UserRepository;
import com.example.job_portal_ai.service.impl.ResumeAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import  java .util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResumeAnalysisServiceImpl implements ResumeAnalysisService {
    private final ResumeAnalysisRepository resumeAnalysisRepository;
    private final UserRepository userRepository;
    private final CandidateProfileRepository candidateProfileRepository;

    @Override
    public ResumeAnalysisDto analyzeResume(String resumeText ,  String fileName) {

        ResumeAnalysisDto analysis = new ResumeAnalysisDto();
        User user = getCurrentUser();

        analysis.setCandidateName(user.getName());
        analysis.setEmail(user.getEmail());
        analysis.setResumeUrl(fileName);


        analysis.setPhone(extractPhone(resumeText));
        analysis.setSkills(extractSkills(resumeText));
        analysis.setEducation(extractEducation(resumeText));
        analysis.setExperience(extractExperience(resumeText));
        analysis.setGithubUrl(extractGithubUrl(resumeText));
        analysis.setLocation(extractLocation(resumeText));
        analysis.setProjects(extractProjects(resumeText));

        ResumeAnalysis savedAnalysis = saveAnalysis(analysis);

        analysis.setUploadedAt(savedAnalysis.getUploadedAt());

        return analysis;
    }


    private String extractName(String text){

        return text.split("\n")[0].trim();

    }


    private String extractEmail(String text){

        String[] words = text.split("\\s+");

        return Arrays.stream(words)
                .filter(word -> word.contains("@"))
                .findFirst()
                .orElse(null);
    }


    private List<String> extractSkills(String text) {

        List<String> skillList = Arrays.asList(
                "Java",
                "Spring Boot",
                "Hibernate",
                "SQL",
                "PostgreSQL",
                "MySQL",
                "Git",
                "Docker",
                "HTML",
                "CSS",
                "React"
        );

        return skillList.stream()
                .filter(skill -> text.toLowerCase().contains(skill.toLowerCase()))
                .collect(Collectors.toList());
    }

    private String extractPhone(String text){

        Pattern pattern = Pattern.compile(
                "(\\+91[-\\s]?)?[6-9]\\d{9}"
        );

        Matcher matcher = pattern.matcher(text);

        if(matcher.find()){
            return matcher.group();
        }

        return null;
    }

    private List<String> extractEducation(String text){

        List<String> educationList = Arrays.asList(
                "MCA",
                "BCA",
                "B.Tech",
                "M.Tech",
                "Bachelor",
                "Master",
                "MBA",
                "B.Sc",
                "M.Sc"
        );

        return educationList.stream()
                .filter(education -> text.toLowerCase().contains(education.toLowerCase()))
                .collect(Collectors.toList());
    }

    private String extractExperience(String text){

        String lowerText = text.toLowerCase();

        if(lowerText.contains("fresher")){
            return "Fresher";
        }

        if(lowerText.contains("internship") ||
                lowerText.contains("intern")){
            return "Internship";
        }

        Pattern pattern = Pattern.compile(
                "(\\d+)\\+?\\s*(years|year)"
        );

        Matcher matcher = pattern.matcher(lowerText);

        if(matcher.find()){
            return matcher.group();
        }

        if(lowerText.contains("experience")){
            return "Experience Mentioned";
        }

        return "Not Mentioned";
    }

    private String extractGithubUrl(String text){

        Pattern pattern = Pattern.compile(
                "https?://(www\\.)?github\\.com/\\S+"
        );

        Matcher matcher = pattern.matcher(text);

        if(matcher.find()){
            return matcher.group();
        }

        return null;
    }

    private String extractLocation(String text){

        Pattern pattern = Pattern.compile(
                "\\d{10}\\s+([A-Za-z ]+,\\s*[A-Za-z]+)"
        );

        Matcher matcher = pattern.matcher(text);

        if(matcher.find()){
            return matcher.group(1);
        }

        return null;
    }

    private List<String> extractProjects(String text){

        List<String> projects = new ArrayList<>();

        String[] lines = text.split("\n");

        boolean projectSection = false;

        for(String line : lines){

            line = line.trim();

            if(line.toLowerCase().contains("personal projects")
                    || line.toLowerCase().equals("projects")){

                projectSection = true;
                continue;
            }

            if(projectSection){

                if(line.toLowerCase().contains("education")
                        || line.toLowerCase().contains("skills")
                        || line.toLowerCase().contains("certificates")){

                    break;
                }


                // skip unwanted lines
                if(line.isEmpty()
                        || line.contains("GitHub")
                        || line.contains("github")
                        || line.matches("^(React|Node.js|Express.js|PostgreSQL|MySQL|JDBC|SQL|API).*")
                        || line.endsWith(".")
                        || line.length() < 5
                        || line.startsWith("Built")
                        || line.startsWith("Implemented")
                        || line.startsWith("Developed")
                        || line.startsWith("Used")
                        || line.startsWith("Designed")){

                    continue;
                }


                projects.add(
                        line.split("\\|")[0].trim()
                );
            }
        }

        return projects;
    }

    private ResumeAnalysis saveAnalysis(ResumeAnalysisDto dto){

        User user = getCurrentUser();

        ResumeAnalysis analysis =
                resumeAnalysisRepository
                        .findByUser(user)
                        .orElse(new ResumeAnalysis());

        analysis.setUser(user);

        // resume extracted data
        analysis.setPhone(dto.getPhone());
        analysis.setLocation(dto.getLocation());
        analysis.setSkills(dto.getSkills());
        analysis.setEducation(dto.getEducation());
        analysis.setProjects(dto.getProjects());
        analysis.setExperience(dto.getExperience());
        analysis.setGithubUrl(dto.getGithubUrl());

        System.out.println("Skills: " + analysis.getSkills());
        System.out.println("Education: " + analysis.getEducation());
        System.out.println("Projects: " + analysis.getProjects());

        ResumeAnalysis savedAnalysis =
                resumeAnalysisRepository.save(analysis);

        updateCandidateProfile(user, savedAnalysis,dto.getResumeUrl());

        return savedAnalysis;
    }
    private User getCurrentUser(){

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public ResumeAnalysisDto getMyAnalysis(){

        User user = getCurrentUser();

        ResumeAnalysis analysis = resumeAnalysisRepository
                .findByUserId(user.getId())
                .orElseThrow(() ->
                        new RuntimeException("Resume analysis not found")
                );


        CandidateProfile profile = candidateProfileRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException("Candidate profile not found")
                );


        ResumeAnalysisDto dto = new ResumeAnalysisDto();
        dto.setCandidateName(user.getName());
        dto.setEmail(user.getEmail());

        // From ResumeAnalysis table
        dto.setPhone(analysis.getPhone());
        dto.setLocation(analysis.getLocation());
        dto.setSkills(analysis.getSkills());
        dto.setEducation(analysis.getEducation());
        dto.setProjects(analysis.getProjects());
        dto.setExperience(analysis.getExperience());
        dto.setGithubUrl(analysis.getGithubUrl());

        // From CandidateProfile table
        dto.setResumeUrl(profile.getResumeUrl());

        dto.setUploadedAt(analysis.getUploadedAt());

        return dto;
    }
    private void updateCandidateProfile(
            User user,
            ResumeAnalysis analysis,
           String resumeUrl)
    {

        CandidateProfile profile = candidateProfileRepository
                .findByUser(user)
                .orElse(
                        CandidateProfile.builder()
                                .user(user)
                                .build()
                );


        profile.setPhone(analysis.getPhone());
        profile.setResumeUrl(resumeUrl);

        // Resume location maps to candidate address
        profile.setAddress(analysis.getLocation());

        profile.setSkills(String.join(", ", analysis.getSkills()));

        profile.setEducation(String.join(", ", analysis.getEducation()));

        profile.setExperience(analysis.getExperience());

        profile.setGithubUrl(analysis.getGithubUrl());

        profile.setResumeUploaded(true);

        profile.setUploadedAt(LocalDateTime.now());
        System.out.println("---- Updating Candidate Profile ----");
        System.out.println("USER: " + user.getEmail());
        System.out.println("PHONE: " + analysis.getPhone());
        System.out.println("LOCATION: " + analysis.getLocation());
        System.out.println("SKILLS: " + analysis.getSkills());
        System.out.println("GITHUB: " + analysis.getGithubUrl());

        System.out.println("Candidate Profile ID: " + profile.getId());
        System.out.println("Address before save: " + profile.getAddress());
        System.out.println("Resume URL before save: " + profile.getResumeUrl());
        System.out.println("Phone before save: " + profile.getPhone());

        candidateProfileRepository.save(profile);

        //What this method does:
        //Finds the logged-in candidate profile
        //If profile does not exist → creates it
        //Copies extracted resume data
        //Marks resume as uploaded
        //Saves it
    }
}
