package com.example.job_portal_ai.exception;

public class JobNotFoundException extends RuntimeException {

    public JobNotFoundException(String message){
        super(message);
    }
}
