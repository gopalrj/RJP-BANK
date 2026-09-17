package com.bank.customer.repository;

import com.bank.customer.entity.Customer;
import com.bank.customer.entity.CustomerStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByPanNumber(String panNumber);

    List<Customer> findByStatus(CustomerStatus status);

    boolean existsByEmailOrPanNumber(String email, String panNumber);
}
