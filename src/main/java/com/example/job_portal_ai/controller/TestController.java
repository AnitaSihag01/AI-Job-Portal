package com.example.job_portal_ai.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


    @RestController
    @RequestMapping("/api/test")
    public class TestController {


        @GetMapping("/candidate")
        @PreAuthorize("hasRole('CANDIDATE')")
        public String candidateAccess(){
            System.out.println(
                    "AUTH IN CONTROLLER: " +
                            SecurityContextHolder.getContext()
                                    .getAuthentication()
            );
            return "Candidate access granted";
        }


        @GetMapping("/recruiter")
        @PreAuthorize("hasRole('RECRUITER')")
        public String recruiterAccess(){
            System.out.println("RECRUITER METHOD CALLED");

            return "Recruiter access granted";
        }

        @GetMapping("/whoami")
        public Object whoAmI(){

            return SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getAuthorities();
        }


}
