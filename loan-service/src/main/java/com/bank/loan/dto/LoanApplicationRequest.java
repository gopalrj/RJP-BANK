package com.bank.loan.dto;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class LoanApplicationRequest {

    @NotNull
    private Long customerId;

    @NotNull
    @DecimalMin(value = "1000.0", message = "amount must be at least 1000")
    private BigDecimal amount;

    @Min(3)
    @Max(360)
    private int tenureMonths;

    @NotBlank
    private String purpose;

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

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public int getTenureMonths() { return tenureMonths; }
    public void setTenureMonths(int tenureMonths) { this.tenureMonths = tenureMonths; }

    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }

    public BigDecimal getAnnualIncome() { return annualIncome; }
    public void setAnnualIncome(BigDecimal annualIncome) { this.annualIncome = annualIncome; }

    public int getExistingLoanCount() { return existingLoanCount; }
    public void setExistingLoanCount(int existingLoanCount) { this.existingLoanCount = existingLoanCount; }

    public int getCreditScore() { return creditScore; }
    public void setCreditScore(int creditScore) { this.creditScore = creditScore; }
}
