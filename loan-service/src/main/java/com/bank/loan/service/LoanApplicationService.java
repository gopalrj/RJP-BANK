package com.bank.loan.service;

import com.bank.loan.dto.LoanApplicationRequest;
import com.bank.loan.dto.LoanApplicationResponse;
import com.bank.loan.dto.LoanPortfolioSummary;

import java.util.List;

public interface LoanApplicationService {

    LoanApplicationResponse apply(LoanApplicationRequest request);

    LoanApplicationResponse getById(Long id);

    List<LoanApplicationResponse> getByCustomer(Long customerId);

    LoanPortfolioSummary getPortfolioSummary();
}
