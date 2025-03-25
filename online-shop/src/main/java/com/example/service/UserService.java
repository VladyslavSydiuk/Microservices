package com.example.service;

import com.example.model.User;
import com.example.model.dto.UserDTO;

public interface UserService {

    User findById(Long userId);
    User registerUser(UserDTO userDTO);
    String verify(UserDTO userDTO);

}
