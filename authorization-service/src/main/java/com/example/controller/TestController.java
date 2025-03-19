package com.example.controller;

import com.example.model.Client;
import com.example.repo.ClientRepository;
import com.example.repo.JpaRegisteredClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    private final ClientRepository clientRepository;
    private final JpaRegisteredClientRepository registeredClientRepository;

    @Autowired
    public TestController(ClientRepository clientRepository, JpaRegisteredClientRepository registeredClientRepository) {
        this.clientRepository = clientRepository;
        this.registeredClientRepository = registeredClientRepository;
    }

    @GetMapping("/client")
    public Client getClient(@RequestParam String clientId) {
        return clientRepository
                .findByClientId(clientId)
                .orElseThrow(() -> new RuntimeException("Client with ID " + clientId + " not found"));
    }

    @GetMapping("/registeredClient")
    public RegisteredClient getRegisteredClient(@RequestParam String clientId) {
        return registeredClientRepository.findById(clientId);
    }

    @GetMapping("/registered")
    public RegisteredClient getRegisteredClientByClientId(@RequestParam String clientId) {
        return registeredClientRepository.findByClientId(clientId);
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }

    @GetMapping("/public")
    public String free() {
        return "For all";
    }
}
