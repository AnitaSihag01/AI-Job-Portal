package com.example.job_portal_ai;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {


    @Test
    void generatePassword(){

        BCryptPasswordEncoder encoder =
                new BCryptPasswordEncoder();

        String password = "Rohan@123";

        String encodedPassword =
                encoder.encode(password);

        System.out.println(encodedPassword);
    }
}
