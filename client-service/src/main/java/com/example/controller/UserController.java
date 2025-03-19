package com.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/client")
public class UserController {

    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> getCurrentUser(Principal principal) {
        if (principal instanceof OAuth2AuthenticationToken token) {
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("sub", token.getPrincipal().getName());
            userInfo.put("client-id", token.getPrincipal().getAttribute("aud"));
            userInfo.put("issuer", token.getPrincipal().getAttribute("iss"));
            userInfo.put("roles", token.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList());
            return ResponseEntity.ok(userInfo);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "User is not authenticated"));
        }
    }
}


