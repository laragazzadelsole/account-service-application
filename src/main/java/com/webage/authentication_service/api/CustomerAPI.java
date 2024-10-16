package com.webage.authentication_service.api;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.webage.authentication_service.domain.Customer;
import com.webage.authentication_service.repository.CustomersRepository;

@RestController
@RequestMapping("/api/customers")
public class CustomerAPI {
    
    @Autowired  CustomersRepository repo;

    @GetMapping
    public ResponseEntity<?> getAll(RequestEntity re) {
        return ResponseEntity.ok(repo.findAll());
    }

    
    @GetMapping("/{id}")
    public Optional<Customer> getCustomer(@PathVariable long id) {
        return repo.findById(id);
    }

    @PostMapping
    public ResponseEntity<?> addCustomer(@RequestBody Customer newCustomer) {
        // Validate input:
        if ( newCustomer.getName()==null
            || newCustomer.getEmail() == null) { 
            return ResponseEntity.badRequest().build();
        }
        newCustomer=repo.save(newCustomer);

        URI location =
            ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(newCustomer.getId())
            .toUri();

        return ResponseEntity.created(location).build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> putCustomer(
        @RequestBody Customer customer, 
        @PathVariable long id) {

        if (customer.getId()!=id
            || customer.getName()==null
            || customer.getEmail() == null) {
                return ResponseEntity.badRequest().build();
        }

        repo.save(customer);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(
        @PathVariable long id) {
            Optional<Customer> customerExist = repo.findById(id);
            if (customerExist.isPresent()){
                repo.delete(customerExist.get());
            }
        return ResponseEntity.ok().build();
    }
    
}
