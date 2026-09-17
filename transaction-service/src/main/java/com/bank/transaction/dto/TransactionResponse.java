package com.bank.transaction.dto;

import com.bank.transaction.entity.TransactionStatus;
import com.bank.transaction.entity.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionResponse {

    private Long id;
    private String transactionRef;
    private String accountNumber;
    private Long loanId;
    private BigDecimal amount;
    private TransactionType type;
    private TransactionStatus status;
    private String description;
    private LocalDateTime timestamp;

    public TransactionResponse() {
    }

    public TransactionResponse(Long id, String transactionRef, String accountNumber, Long loanId, BigDecimal amount,
                                TransactionType type, TransactionStatus status, String description,
                                LocalDateTime timestamp) {
        this.id = id;
        this.transactionRef = transactionRef;
        this.accountNumber = accountNumber;
        this.loanId = loanId;
        this.amount = amount;
        this.type = type;
        this.status = status;
        this.description = description;
        this.timestamp = timestamp;
    }

    public Long getId() { return id; }
    public String getTransactionRef() { return transactionRef; }
    public String getAccountNumber() { return accountNumber; }
    public Long getLoanId() { return loanId; }
    public BigDecimal getAmount() { return amount; }
    public TransactionType getType() { return type; }
    public TransactionStatus getStatus() { return status; }
    public String getDescription() { return description; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
