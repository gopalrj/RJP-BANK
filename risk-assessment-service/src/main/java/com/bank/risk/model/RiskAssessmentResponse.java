package com.bank.risk.model;

import java.time.LocalDateTime;
import java.util.List;

public class RiskAssessmentResponse {

    private Long customerId;
    private int riskScore;
    private RiskLevel riskLevel;
    private boolean approved;
    private double debtToIncomeRatio;
    private List<String> remarks;
    private LocalDateTime assessedAt;

    public RiskAssessmentResponse() {
    }

    public RiskAssessmentResponse(Long customerId, int riskScore, RiskLevel riskLevel, boolean approved,
                                   double debtToIncomeRatio, List<String> remarks) {
        this.customerId = customerId;
        this.riskScore = riskScore;
        this.riskLevel = riskLevel;
        this.approved = approved;
        this.debtToIncomeRatio = debtToIncomeRatio;
        this.remarks = remarks;
        this.assessedAt = LocalDateTime.now();
    }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public int getRiskScore() { return riskScore; }
    public void setRiskScore(int riskScore) { this.riskScore = riskScore; }

    public RiskLevel getRiskLevel() { return riskLevel; }
    public void setRiskLevel(RiskLevel riskLevel) { this.riskLevel = riskLevel; }

    public boolean isApproved() { return approved; }
    public void setApproved(boolean approved) { this.approved = approved; }

    public double getDebtToIncomeRatio() { return debtToIncomeRatio; }
    public void setDebtToIncomeRatio(double debtToIncomeRatio) { this.debtToIncomeRatio = debtToIncomeRatio; }

    public List<String> getRemarks() { return remarks; }
    public void setRemarks(List<String> remarks) { this.remarks = remarks; }

    public LocalDateTime getAssessedAt() { return assessedAt; }
    public void setAssessedAt(LocalDateTime assessedAt) { this.assessedAt = assessedAt; }
}
