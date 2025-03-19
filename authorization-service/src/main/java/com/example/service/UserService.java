package com.example.service;

import com.example.dto.UserDTO;
import com.example.model.UserEntity;

public interface UserService {

    UserEntity registerNewUser(UserDTO userDTO);

}
