package com.bank.loan.client;

import java.math.BigDecimal;

/** Mirrors risk-assessment-service's RiskAssessmentRequest for the Feign contract. */
public class RiskAssessmentRequestDto {

    private Long customerId;
    private BigDecimal loanAmount;
    private int tenureMonths;
    private BigDecimal annualIncome;
    private int existingLoanCount;
    private int creditScore;

    public RiskAssessmentRequestDto() {
    }

    public RiskAssessmentRequestDto(Long customerId, BigDecimal loanAmount, int tenureMonths,
                                     BigDecimal annualIncome, int existingLoanCount, int creditScore) {
        this.customerId = customerId;
        this.loanAmount = loanAmount;
        this.tenureMonths = tenureMonths;
        this.annualIncome = annualIncome;
        this.existingLoanCount = existingLoanCount;
        this.creditScore = creditScore;
    }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    public BigDecimal getLoanAmount() { return loanAmount; }
    public void setLoanAmount(BigDecimal loanAmount) { this.loanAmount = loanAmount; }
    public int getTenureMonths() { return tenureMonths; }
    public void setTenureMonths(int tenureMonths) { this.tenureMonths = tenureMonths; }
    public BigDecimal getAnnualIncome() { return annualIncome; }
    public void setAnnualIncome(BigDecimal annualIncome) { this.annualIncome = annualIncome; }
    public int getExistingLoanCount() { return existingLoanCount; }
    public void setExistingLoanCount(int existingLoanCount) { this.existingLoanCount = existingLoanCount; }
    public int getCreditScore() { return creditScore; }
    public void setCreditScore(int creditScore) { this.creditScore = creditScore; }
}
