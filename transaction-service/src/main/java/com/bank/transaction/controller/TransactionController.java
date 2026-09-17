package com.bank.transaction.controller;

import com.bank.transaction.dto.AccountStatement;
import com.bank.transaction.dto.TransactionRequest;
import com.bank.transaction.dto.TransactionResponse;
import com.bank.transaction.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> process(@Valid @RequestBody TransactionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.process(request));
    }

    @GetMapping("/{transactionRef}")
    public ResponseEntity<TransactionResponse> getByRef(@PathVariable String transactionRef) {
        return ResponseEntity.ok(transactionService.getByRef(transactionRef));
    }

    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<List<TransactionResponse>> getByAccount(@PathVariable String accountNumber) {
        return ResponseEntity.ok(transactionService.getByAccount(accountNumber));
    }

    @GetMapping("/account/{accountNumber}/statement")
    public ResponseEntity<AccountStatement> getStatement(@PathVariable String accountNumber) {
        return ResponseEntity.ok(transactionService.getStatement(accountNumber));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(IllegalArgumentException ex) {
        Map<String, String> body = new LinkedHashMap<>();
        body.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }
}
