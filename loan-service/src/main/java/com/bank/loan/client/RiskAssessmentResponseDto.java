package com.bank.loan.client;

import java.util.List;

public class RiskAssessmentResponseDto {

    private Long customerId;
    private int riskScore;
    private String riskLevel;
    private boolean approved;
    private double debtToIncomeRatio;
    private List<String> remarks;

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    public int getRiskScore() { return riskScore; }
    public void setRiskScore(int riskScore) { this.riskScore = riskScore; }
    public String getRiskLevel() { return riskLevel; }
    public void setRiskLevel(String riskLevel) { this.riskLevel = riskLevel; }
    public boolean isApproved() { return approved; }
    public void setApproved(boolean approved) { this.approved = approved; }
    public double getDebtToIncomeRatio() { return debtToIncomeRatio; }
    public void setDebtToIncomeRatio(double debtToIncomeRatio) { this.debtToIncomeRatio = debtToIncomeRatio; }
    public List<String> getRemarks() { return remarks; }
    public void setRemarks(List<String> remarks) { this.remarks = remarks; }
}
