package com.webage.authentication_service.controller;

import com.webage.authentication_service.domain.Customer;
import com.webage.authentication_service.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AuthService authService;

    @GetMapping
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Account service is up and running.");
    }

    @PostMapping("/token")
    public ResponseEntity<String> getToken(@RequestParam String email, @RequestParam String password) {
        String token = authService.login(email, password);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Customer customer) {
        authService.register(customer);
        return ResponseEntity.ok("Customer registered successfully.");
    }
}

