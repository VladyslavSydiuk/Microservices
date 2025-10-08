package com.example.service.impl;

import com.example.model.User;
import com.example.model.enums.Role;
import com.example.model.dto.UserDTO;
import com.example.repository.UserRepository;
import com.example.service.JWTService;
import com.example.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    public UserServiceImpl(UserRepository userRepository, JWTService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public User registerUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(encoder.encode(userDTO.getPassword()));
        user.setRoles(List.of(Role.USER));
        return userRepository.save(user);
    }

    @Override
    public String verify(UserDTO userDTO) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword()));
        if (authentication.isAuthenticated()) {
            // fetch email to embed in JWT claims
            String email = userRepository.findByUsername(userDTO.getUsername())
                    .map(User::getEmail)
                    .orElse(null);
            return jwtService.generateToken(userDTO.getUsername(), email);
        }

        return "Fail";

    }

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow();
    }
}
