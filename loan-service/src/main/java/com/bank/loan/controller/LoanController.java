package com.bank.loan.controller;

import com.bank.loan.dto.LoanApplicationRequest;
import com.bank.loan.dto.LoanApplicationResponse;
import com.bank.loan.dto.LoanPortfolioSummary;
import com.bank.loan.service.LoanApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanApplicationService loanApplicationService;

    public LoanController(LoanApplicationService loanApplicationService) {
        this.loanApplicationService = loanApplicationService;
    }

    @PostMapping("/apply")
    public ResponseEntity<LoanApplicationResponse> apply(@Valid @RequestBody LoanApplicationRequest request) {
        LoanApplicationResponse response = loanApplicationService.apply(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanApplicationResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(loanApplicationService.getById(id));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<LoanApplicationResponse>> getByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(loanApplicationService.getByCustomer(customerId));
    }

    @GetMapping("/summary")
    public ResponseEntity<LoanPortfolioSummary> getPortfolioSummary() {
        return ResponseEntity.ok(loanApplicationService.getPortfolioSummary());
    }
}
