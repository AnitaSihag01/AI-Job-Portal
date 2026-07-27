package com.example.job_portal_ai.dto;

import com.example.job_portal_ai.entity.type.Role;
import lombok.Data;

@Data
public class RegisterRequest {

    private String name;
    private String email;
    private String password;
    private Role role;
}
