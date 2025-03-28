package com.example.security;

import com.example.model.User;
import com.example.model.enums.Role;
import com.example.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
public class OAuthFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;



    public OAuthFilter(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof OAuth2User oauth2User) {
            processOAuthUser(oauth2User);
        }

        filterChain.doFilter(request, response);
    }

    private void processOAuthUser(OAuth2User oauth2User) {
        String username = oauth2User.getName();
        String email = oauth2User.getAttribute("email");

        if (userRepository.findByUsername(username).isEmpty()) {
            User newUser = createNewUser(username, email);
            userRepository.save(newUser);
            log.info("New OAuth2 user registered: {}", username);
        } else {
            log.info("OAuth2 user authenticated: {}", username);
        }
    }

    private User createNewUser(String username, String email) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        String rawPassword = username + email + UUID.randomUUID();
        user.setPassword(passwordEncoder.encode(rawPassword));

        user.setRoles(List.of(Role.USER));
        return user;
    }
}
