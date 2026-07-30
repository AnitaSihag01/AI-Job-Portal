package com.example.job_portal_ai.security;

import com.example.job_portal_ai.entity.User;
import com.example.job_portal_ai.exception.JwtAuthenticationException;
import com.example.job_portal_ai.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtSecurity jwtSecurity;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = authHeader.substring(7);

        System.out.println("TOKEN RECEIVED: " + token);
        try {

            String email = jwtSecurity.extractUsername(token);

            User user = userRepository.findByEmail(email)
                    .orElseThrow(() ->
                            new JwtAuthenticationException("User not found"));


            if(jwtSecurity.isTokenValid(token, user)) {

                List<GrantedAuthority> authorities =
                        List.of(new SimpleGrantedAuthority(
                                "ROLE_" + user.getRole()
                        ));

                System.out.println("ROLE FROM DB: " + user.getRole());
                System.out.println("AUTHORITIES: " + authorities);


                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                user,
                                null,
                                authorities
                        );


                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
            }

        }
        catch(Exception e){

            throw new JwtAuthenticationException(
                    "Token expired or invalid"
            );
        }
        filterChain.doFilter(request, response);

    }
}
