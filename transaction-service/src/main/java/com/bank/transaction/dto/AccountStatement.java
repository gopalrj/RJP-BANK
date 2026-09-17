package com.bank.transaction.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/** Statement summary computed entirely with Java 8 Streams/Collectors. */
public class AccountStatement {

    private String accountNumber;
    private BigDecimal totalCredits;
    private BigDecimal totalDebits;
    private BigDecimal netBalance;
    private Map<String, BigDecimal> totalsByType;
    private List<TransactionResponse> transactions;

    public AccountStatement() {
    }

    public AccountStatement(String accountNumber, BigDecimal totalCredits, BigDecimal totalDebits,
                             BigDecimal netBalance, Map<String, BigDecimal> totalsByType,
                             List<TransactionResponse> transactions) {
        this.accountNumber = accountNumber;
        this.totalCredits = totalCredits;
        this.totalDebits = totalDebits;
        this.netBalance = netBalance;
        this.totalsByType = totalsByType;
        this.transactions = transactions;
    }

    public String getAccountNumber() { return accountNumber; }
    public BigDecimal getTotalCredits() { return totalCredits; }
    public BigDecimal getTotalDebits() { return totalDebits; }
    public BigDecimal getNetBalance() { return netBalance; }
    public Map<String, BigDecimal> getTotalsByType() { return totalsByType; }
    public List<TransactionResponse> getTransactions() { return transactions; }
}
