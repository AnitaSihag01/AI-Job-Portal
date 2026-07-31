package com.example.job_portal_ai.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "jobs")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String title;


    @Column(nullable = false, length = 2000)
    private String description;


    @Column(nullable = false)
    private String requiredSkills;


    @Column(nullable = false)
    private String experience;


    @Column(nullable = false)
    private String salary;


    @Column(nullable = false)
    private String location;


    @Column(nullable = false)
    private String employmentType;


    @ManyToOne
    @JoinColumn(name = "recruiter_id", nullable = false)
    private User recruiter;

}
