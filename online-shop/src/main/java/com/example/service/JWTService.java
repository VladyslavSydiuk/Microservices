package com.example.service;

    import org.springframework.security.core.userdetails.UserDetails;

    public interface JWTService {

        String generateToken(String username);

        String generateToken(String username, String email);

        String extractUserName(String token);

        boolean validateToken(String token, UserDetails userDetails);
    }
