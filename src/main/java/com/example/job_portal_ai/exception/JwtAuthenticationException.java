package com.example.job_portal_ai.exception;

public class JwtAuthenticationException extends RuntimeException {

    public JwtAuthenticationException(String message) {
        super(message);
    }

    public String getMessage() {

        return "";
    }
}
