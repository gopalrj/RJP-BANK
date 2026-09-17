package com.bank.customer.service;

import com.bank.customer.dto.CustomerRegistrationRequest;
import com.bank.customer.dto.CustomerResponse;
import com.bank.customer.entity.CustomerStatus;

import java.util.List;

public interface CustomerService {

    CustomerResponse register(CustomerRegistrationRequest request);

    CustomerResponse getById(Long id);

    List<CustomerResponse> getAll();

    List<CustomerResponse> getByStatus(CustomerStatus status);

    CustomerResponse updateStatus(Long id, CustomerStatus status);

    boolean exists(Long id);
}
