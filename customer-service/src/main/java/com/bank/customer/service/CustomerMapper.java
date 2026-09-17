package com.bank.customer.service;

import com.bank.customer.dto.CustomerResponse;
import com.bank.customer.entity.Customer;

import java.util.function.Function;

/**
 * Java 8 functional interface: entity -> DTO mapping expressed as a
 * reusable Function reference instead of a static utility method call,
 * so it can be passed straight into Stream.map(...).
 */
public final class CustomerMapper {

    private CustomerMapper() {
    }

    public static final Function<Customer, CustomerResponse> TO_RESPONSE = customer -> new CustomerResponse(
            customer.getId(),
            customer.getFullName(),
            customer.getEmail(),
            customer.getPhone(),
            customer.getDateOfBirth(),
            customer.getAddress(),
            customer.getPanNumber(),
            customer.getRegistrationDate(),
            customer.getStatus()
    );
}
