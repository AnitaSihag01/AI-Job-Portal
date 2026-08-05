package com.example.job_portal_ai.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true) // One candidate can have only one profile.
    private User user;

    private String phone;

    private String address;

    @Column(length = 1000)
    private String skills;

    private String experience;

    private String education;

    private String githubUrl;

    private String linkedinUrl;

    private String resumeUrl;

    private boolean resumeUploaded;

    private LocalDateTime uploadedAt;

}