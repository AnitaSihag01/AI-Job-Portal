package com.example.job_portal_ai.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponse {


    private LocalDateTime timestamp;
    private int status;
    private String message;
}
