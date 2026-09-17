package com.bank.customer.service;

import com.bank.customer.dto.CustomerRegistrationRequest;
import com.bank.customer.dto.CustomerResponse;
import com.bank.customer.entity.Customer;
import com.bank.customer.entity.CustomerStatus;
import com.bank.customer.exception.CustomerNotFoundException;
import com.bank.customer.exception.DuplicateCustomerException;
import com.bank.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerResponse register(CustomerRegistrationRequest request) {
        if (customerRepository.existsByEmailOrPanNumber(request.getEmail(), request.getPanNumber())) {
            throw new DuplicateCustomerException(
                    "A customer already exists with this email or PAN number");
        }

        Customer customer = new Customer(
                request.getFullName(),
                request.getEmail(),
                request.getPhone(),
                request.getDateOfBirth(),
                request.getAddress(),
                request.getPanNumber()
        );

        Customer saved = customerRepository.save(customer);
        return CustomerMapper.TO_RESPONSE.apply(saved);
    }

    @Override
    public CustomerResponse getById(Long id) {
        // Java 8 Optional chained straight into the mapper Function, no null checks
        return customerRepository.findById(id)
                .map(CustomerMapper.TO_RESPONSE)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));
    }

    @Override
    public List<CustomerResponse> getAll() {
        return customerRepository.findAll().stream()
                .map(CustomerMapper.TO_RESPONSE)
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerResponse> getByStatus(CustomerStatus status) {
        return customerRepository.findByStatus(status).stream()
                .map(CustomerMapper.TO_RESPONSE)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerResponse updateStatus(Long id, CustomerStatus status) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));
        customer.setStatus(status);
        return CustomerMapper.TO_RESPONSE.apply(customerRepository.save(customer));
    }

    @Override
    public boolean exists(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.isPresent();
    }
}
