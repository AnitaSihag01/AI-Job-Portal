package com.example.job_portal_ai.controller;

import com.example.job_portal_ai.dto.CandidateDashboardDto;
import com.example.job_portal_ai.service.impl.CandidateDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/candidate")
@RequiredArgsConstructor
public class CandidateDashboradController {


    private final CandidateDashboardService candidateDashboardService;


    @GetMapping("/dashboard")
    public CandidateDashboardDto getDashboard(){

        return candidateDashboardService.getDashboard();

    }
}