package com.example.job_portal_ai.service;


import com.example.job_portal_ai.dto.LoginRequestDto;
import com.example.job_portal_ai.dto.LoginResponseDto;
import com.example.job_portal_ai.dto.RegisterRequest;
import com.example.job_portal_ai.dto.RegisterResponse;
import com.example.job_portal_ai.entity.User;
import com.example.job_portal_ai.entity.type.Role;
import com.example.job_portal_ai.exception.EmailAlreadyExistsException;
import com.example.job_portal_ai.exception.InvalidCredentialsException;
import com.example.job_portal_ai.exception.UserNotFoundException;
import com.example.job_portal_ai.repository.UserRepository;
import com.example.job_portal_ai.security.JwtSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtSecurity jwtSecurity;

    private  final PasswordEncoder passwordEncoder;

   /* public RegisterResponse register (RegisterRequest request){

        // CONVERT RegisterREquest -> user Entity
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.CANDIDATE)
                .build();

        // Save User into DB
        User savedUser= userRepository.save(user);

        // Convert user entity -> RegisterResponse Dto

        RegisterResponse response= RegisterResponse.builder()
                .id(savedUser.getId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .role(savedUser.getRole())
                .createdAt(savedUser.getCreatedAt())
                .build();

        // return response
        return response;
    }*/

    public LoginResponseDto login(LoginRequestDto request){

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));


        if(!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        )){
            throw new InvalidCredentialsException("Invalid email or password");
        }


        String token = jwtSecurity.generateToken(user);


        LoginResponseDto response = LoginResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .token(token)
                .build();


        return response;
    }

    public RegisterResponse register(RegisterRequest request) {

        return registerUser(request, Role.CANDIDATE);
    }


    private RegisterResponse registerUser(RegisterRequest request, Role role) {

        Optional<User> existingUser =
                userRepository.findByEmail(request.getEmail());

        if (existingUser.isPresent()) {
            throw new EmailAlreadyExistsException("Email already exist");
        }


        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();


        User savedUser = userRepository.save(user);


        return RegisterResponse.builder()
                .id(savedUser.getId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .role(savedUser.getRole())
                .createdAt(savedUser.getCreatedAt())
                .build();
    }
    public RegisterResponse registerRecruiter(RegisterRequest request) {

        return registerUser(request, Role.RECRUITER);
    }
}
