package com.example.job_portal_ai.entity;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class ResumeAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phone;

    private String location;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "resume_analysis_skills",
            joinColumns = @JoinColumn(name = "resume_analysis_id")
    )
    @Column(name = "skill")
    private List<String> skills = new ArrayList<>();  //  ElementCollection -> because skills can have multiple values

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "resume_analysis_education",
            joinColumns = @JoinColumn(name = "resume_analysis_id")
    )
    @Column(name = "education")
    private List<String> education = new ArrayList<>();

    // This tells Hibernate: Whenever ResumeAnalysis is loaded, also load skills, education, and projects.
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "resume_analysis_projects",
            joinColumns = @JoinColumn(name = "resume_analysis_id")
    )
    @Column(name = "project")
    private List<String> projects =  new ArrayList<>();

    private String experience;

    private String githubUrl;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @CreationTimestamp
    private LocalDateTime uploadedAt;
}