package com.bank.risk.service;

import com.bank.risk.model.*;
import com.bank.risk.rules.RuleEngine;
import com.bank.risk.rules.RuleOutcome;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class RiskAssessmentServiceImpl implements RiskAssessmentService {

    private static final int BASE_SCORE = 50;

    private final RuleEngine ruleEngine;
    private final CreditBureauClient creditBureauClient;

    public RiskAssessmentServiceImpl(RuleEngine ruleEngine, CreditBureauClient creditBureauClient) {
        this.ruleEngine = ruleEngine;
        this.creditBureauClient = creditBureauClient;
    }

    @Override
    public RiskAssessmentResponse assess(RiskAssessmentRequest request) {
        // Java 8 CompletableFuture: call the third-party bureau asynchronously
        // while the local rule engine evaluates in parallel, then combine.
        CompletableFuture<CreditBureauResponse> bureauFuture = CompletableFuture.supplyAsync(
                () -> creditBureauClient.fetchBureauReport(request.getCustomerId(), request.getCreditScore()));

        CompletableFuture<List<RuleOutcome>> rulesFuture = CompletableFuture.supplyAsync(
                () -> ruleEngine.evaluate(request));

        CompletableFuture<RiskAssessmentResponse> combined = bureauFuture.thenCombine(rulesFuture,
                (bureauReport, ruleOutcomes) -> buildResponse(request, bureauReport, ruleOutcomes));

        return combined.join();
    }

    private RiskAssessmentResponse buildResponse(RiskAssessmentRequest request,
                                                   CreditBureauResponse bureauReport,
                                                   List<RuleOutcome> ruleOutcomes) {
        // Java 8 Stream.reduce to fold every rule's score adjustment into one total
        int ruleAdjustment = ruleOutcomes.stream()
                .mapToInt(RuleOutcome::getScoreAdjustment)
                .sum();

        int bureauAdjustment = (bureauReport.getBureauScore() - 600) / 10;
        if (bureauReport.isDefaulterFlag()) {
            bureauAdjustment -= 40;
        }

        int totalScore = clamp(BASE_SCORE + ruleAdjustment + bureauAdjustment, 0, 100);
        RiskLevel level = classify(totalScore);
        boolean approved = totalScore >= 55 && !bureauReport.isDefaulterFlag();

        double debtToIncomeRatio = request.getAnnualIncome().doubleValue() <= 0
                ? 1.0
                : request.getLoanAmount().doubleValue() / request.getAnnualIncome().doubleValue();

        List<String> remarks = ruleOutcomes.stream()
                .map(RuleOutcome::getRemark)
                .filter(remark -> remark != null && !remark.isEmpty())
                .collect(Collectors.toList());

        if (bureauReport.isDefaulterFlag()) {
            remarks.add("Third-party bureau flagged prior default history");
        }

        return new RiskAssessmentResponse(
                request.getCustomerId(), totalScore, level, approved, debtToIncomeRatio, remarks);
    }

    private RiskLevel classify(int score) {
        if (score >= 75) return RiskLevel.LOW;
        if (score >= 55) return RiskLevel.MEDIUM;
        if (score >= 35) return RiskLevel.HIGH;
        return RiskLevel.VERY_HIGH;
    }

    private int clamp(int value, int min, int max) {
        return Math.min(max, Math.max(min, value));
    }
}
