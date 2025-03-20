package com.example.service.impl;

import com.example.dto.UserDTO;
import com.example.model.Role;
import com.example.model.UserEntity;
import com.example.repo.UserRepository;
import com.example.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserEntity registerNewUser(UserDTO userDTO) {
        if(userRepository.existsByUsername(userDTO.getUsername())){
            throw new RuntimeException("User already exists");
        }else {
            return userRepository.save(UserEntity.builder()
                    .username(userDTO.getUsername())
                    .password(passwordEncoder.encode(userDTO.getPassword()))
                    .role(Role.USER)
                    .build());
        }

    }
}
