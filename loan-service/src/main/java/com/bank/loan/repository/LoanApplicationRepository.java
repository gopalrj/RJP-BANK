package com.bank.loan.repository;

import com.bank.loan.entity.LoanApplication;
import com.bank.loan.entity.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {

    List<LoanApplication> findByCustomerId(Long customerId);

    List<LoanApplication> findByStatus(LoanStatus status);
}
