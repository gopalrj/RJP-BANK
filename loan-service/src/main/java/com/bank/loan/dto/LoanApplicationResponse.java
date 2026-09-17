package com.bank.loan.dto;

import com.bank.loan.entity.LoanStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class LoanApplicationResponse {

    private Long id;
    private Long customerId;
    private BigDecimal amount;
    private int tenureMonths;
    private String purpose;
    private LoanStatus status;
    private LocalDateTime appliedDate;
    private LocalDateTime decisionDate;
    private Integer riskScore;
    private String riskLevel;
    private String signatureReference;
    private String remarks;

    public LoanApplicationResponse() {
    }

    public LoanApplicationResponse(Long id, Long customerId, BigDecimal amount, int tenureMonths, String purpose,
                                    LoanStatus status, LocalDateTime appliedDate, LocalDateTime decisionDate,
                                    Integer riskScore, String riskLevel, String signatureReference, String remarks) {
        this.id = id;
        this.customerId = customerId;
        this.amount = amount;
        this.tenureMonths = tenureMonths;
        this.purpose = purpose;
        this.status = status;
        this.appliedDate = appliedDate;
        this.decisionDate = decisionDate;
        this.riskScore = riskScore;
        this.riskLevel = riskLevel;
        this.signatureReference = signatureReference;
        this.remarks = remarks;
    }

    public Long getId() { return id; }
    public Long getCustomerId() { return customerId; }
    public BigDecimal getAmount() { return amount; }
    public int getTenureMonths() { return tenureMonths; }
    public String getPurpose() { return purpose; }
    public LoanStatus getStatus() { return status; }
    public LocalDateTime getAppliedDate() { return appliedDate; }
    public LocalDateTime getDecisionDate() { return decisionDate; }
    public Integer getRiskScore() { return riskScore; }
    public String getRiskLevel() { return riskLevel; }
    public String getSignatureReference() { return signatureReference; }
    public String getRemarks() { return remarks; }
}
