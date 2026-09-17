package com.bank.loan.dto;

import java.math.BigDecimal;
import java.util.Map;

/** Aggregated portfolio statistics computed with Java 8 Streams/Collectors. */
public class LoanPortfolioSummary {

    private long totalApplications;
    private BigDecimal totalAmountRequested;
    private double averageAmount;
    private BigDecimal maxAmount;
    private Map<String, Long> countByStatus;

    public LoanPortfolioSummary() {
    }

    public LoanPortfolioSummary(long totalApplications, BigDecimal totalAmountRequested, double averageAmount,
                                 BigDecimal maxAmount, Map<String, Long> countByStatus) {
        this.totalApplications = totalApplications;
        this.totalAmountRequested = totalAmountRequested;
        this.averageAmount = averageAmount;
        this.maxAmount = maxAmount;
        this.countByStatus = countByStatus;
    }

    public long getTotalApplications() { return totalApplications; }
    public BigDecimal getTotalAmountRequested() { return totalAmountRequested; }
    public double getAverageAmount() { return averageAmount; }
    public BigDecimal getMaxAmount() { return maxAmount; }
    public Map<String, Long> getCountByStatus() { return countByStatus; }
}
