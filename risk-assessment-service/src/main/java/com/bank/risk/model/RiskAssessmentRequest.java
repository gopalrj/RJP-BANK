package com.bank.risk.model;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class RiskAssessmentRequest {

    @NotNull
    private Long customerId;

    @NotNull
    @DecimalMin(value = "1000.0", message = "loanAmount must be at least 1000")
    private BigDecimal loanAmount;

    @Min(3)
    @Max(360)
    private int tenureMonths;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal annualIncome;

    @Min(0)
    private int existingLoanCount;

    @Min(300)
    @Max(900)
    private int creditScore;

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
