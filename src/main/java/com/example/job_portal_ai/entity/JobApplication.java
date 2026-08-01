package com.example.job_portal_ai.entity;

import com.example.job_portal_ai.entity.type.ApplicationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // application id


    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private User candidate; // which user applied ?


    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job; // which job they applied for ?


    @Enumerated(EnumType.STRING)
    private ApplicationStatus status; // current application stage


    private LocalDateTime appliedAt; // date/time when application is subimtted
}