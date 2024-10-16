package com.webage.authentication_service.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.webage.authentication_service.domain.Customer;

public interface CustomersRepository extends CrudRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);

}
