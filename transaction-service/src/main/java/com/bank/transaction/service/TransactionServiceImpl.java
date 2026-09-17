package com.bank.transaction.service;

import com.bank.transaction.dto.AccountStatement;
import com.bank.transaction.dto.TransactionRequest;
import com.bank.transaction.dto.TransactionResponse;
import com.bank.transaction.entity.Transaction;
import com.bank.transaction.entity.TransactionType;
import com.bank.transaction.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    // Java 8 Supplier for generating a unique, traceable transaction reference
    private final Supplier<String> refGenerator =
            () -> "TXN-" + UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();

    // Java 8 Function used as a reusable entity -> DTO mapper
    private final Function<Transaction, TransactionResponse> toResponse = tx -> new TransactionResponse(
            tx.getId(), tx.getTransactionRef(), tx.getAccountNumber(), tx.getLoanId(), tx.getAmount(),
            tx.getType(), tx.getStatus(), tx.getDescription(), tx.getTimestamp());

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public TransactionResponse process(TransactionRequest request) {
        Transaction transaction = new Transaction(
                refGenerator.get(), request.getAccountNumber(), request.getLoanId(),
                request.getAmount(), request.getType(), request.getDescription());
        return toResponse.apply(transactionRepository.save(transaction));
    }

    @Override
    public TransactionResponse getByRef(String transactionRef) {
        return transactionRepository.findAll().stream()
                .filter(tx -> tx.getTransactionRef().equals(transactionRef))
                .findFirst()
                .map(toResponse)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found: " + transactionRef));
    }

    @Override
    public List<TransactionResponse> getByAccount(String accountNumber) {
        // Java 8 Stream: sort by most recent first using a method-reference Comparator
        return transactionRepository.findByAccountNumber(accountNumber).stream()
                .sorted(Comparator.comparing(Transaction::getTimestamp).reversed())
                .map(toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AccountStatement getStatement(String accountNumber) {
        List<Transaction> transactions = transactionRepository.findByAccountNumber(accountNumber);

        // Java 8 Stream + BigDecimal reduce for credit/debit totals
        BigDecimal totalCredits = transactions.stream()
                .filter(tx -> tx.getType() == TransactionType.CREDIT)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalDebits = transactions.stream()
                .filter(tx -> tx.getType() == TransactionType.DEBIT)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal netBalance = totalCredits.subtract(totalDebits);

        // Java 8 Collectors.groupingBy + reducing to total amount per transaction type
        Map<String, BigDecimal> totalsByType = transactions.stream()
                .collect(Collectors.groupingBy(
                        tx -> tx.getType().name(),
                        Collectors.reducing(BigDecimal.ZERO, Transaction::getAmount, BigDecimal::add)));

        List<TransactionResponse> sortedResponses = transactions.stream()
                .sorted(Comparator.comparing(Transaction::getTimestamp).reversed())
                .map(toResponse)
                .collect(Collectors.toList());

        return new AccountStatement(accountNumber, totalCredits, totalDebits, netBalance,
                totalsByType, sortedResponses);
    }
}
