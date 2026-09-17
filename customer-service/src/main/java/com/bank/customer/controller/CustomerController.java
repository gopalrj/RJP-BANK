package com.bank.customer.controller;

import com.bank.customer.dto.CustomerRegistrationRequest;
import com.bank.customer.dto.CustomerResponse;
import com.bank.customer.entity.CustomerStatus;
import com.bank.customer.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/register")
    public ResponseEntity<CustomerResponse> register(@Valid @RequestBody CustomerRegistrationRequest request) {
        CustomerResponse response = customerService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAll(
            @RequestParam(required = false) CustomerStatus status) {
        List<CustomerResponse> customers = (status != null)
                ? customerService.getByStatus(status)
                : customerService.getAll();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> exists(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.exists(id));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<CustomerResponse> updateStatus(@PathVariable Long id,
                                                           @RequestParam CustomerStatus status) {
        return ResponseEntity.ok(customerService.updateStatus(id, status));
    }
}
