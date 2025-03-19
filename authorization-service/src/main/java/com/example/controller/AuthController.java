package com.example.controller;

import com.example.dto.UserDTO;
import com.example.model.UserEntity;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {

    private final UserService userService;
    @Autowired
    public AuthController(UserService userService) {

        this.userService = userService;
    }

    @PostMapping("/register")
    ResponseEntity<UserEntity> registerNewUser(@RequestBody UserDTO userDTO){
        return ResponseEntity.ok(userService.registerNewUser(userDTO));
    }
}
