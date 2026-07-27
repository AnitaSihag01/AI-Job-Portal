package com.example.job_portal_ai.dto;

import com.example.job_portal_ai.entity.type.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDto {

    private Long id;
    private String name;
    private String email;
    private Role role;
    private String token;
}
