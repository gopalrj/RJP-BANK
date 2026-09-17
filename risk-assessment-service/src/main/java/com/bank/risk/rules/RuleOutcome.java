package com.bank.risk.rules;

public class RuleOutcome {

    private final int scoreAdjustment;
    private final String remark;

    public RuleOutcome(int scoreAdjustment, String remark) {
        this.scoreAdjustment = scoreAdjustment;
        this.remark = remark;
    }

    public int getScoreAdjustment() { return scoreAdjustment; }
    public String getRemark() { return remark; }
}
