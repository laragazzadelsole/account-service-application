package com.webage.authentication_service.service;

import com.webage.authentication_service.domain.Customer;
import com.webage.authentication_service.repository.CustomersRepository;
import com.webage.authentication_service.config.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private CustomersRepository customerRepository;

    @Autowired
    private JWTUtil jwtUtil;

    public String login(String email, String password) {
        Optional<Customer> customer = customerRepository.findByEmail(email);
        if (customer.isPresent() && customer.get().getPassword().equals(password)) {
            return jwtUtil.createToken(email);
        }
        throw new RuntimeException("Invalid credentials");
    }

    public void register(Customer customer) {
        customerRepository.save(customer);
    }
}

