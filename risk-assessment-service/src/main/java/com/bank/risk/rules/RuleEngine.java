package com.bank.risk.rules;

import com.bank.risk.model.RiskAssessmentRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Holds the bank's underwriting rule set and evaluates all of them
 * against an application using Java 8 Streams instead of a chain of
 * if/else statements.
 */
@Component
public class RuleEngine {

    private final List<UnderwritingRule> rules = Arrays.asList(
            // Credit score rule
            request -> {
                if (request.getCreditScore() >= 750) return new RuleOutcome(20, "Excellent credit score");
                if (request.getCreditScore() >= 650) return new RuleOutcome(5, "Good credit score");
                if (request.getCreditScore() >= 550) return new RuleOutcome(-15, "Fair credit score");
                return new RuleOutcome(-30, "Poor credit score");
            },
            // Existing loan burden rule
            request -> request.getExistingLoanCount() > 3
                    ? new RuleOutcome(-20, "High number of existing loans")
                    : new RuleOutcome(0, null),
            // Debt-to-income proxy rule based on requested amount vs annual income
            request -> {
                BigDecimal income = request.getAnnualIncome();
                if (income == null || income.compareTo(BigDecimal.ZERO) <= 0) {
                    return new RuleOutcome(-25, "Unverifiable income");
                }
                BigDecimal ratio = request.getLoanAmount()
                        .divide(income, 2, RoundingMode.HALF_UP);
                if (ratio.compareTo(new BigDecimal("0.3")) <= 0) {
                    return new RuleOutcome(15, "Healthy loan-to-income ratio");
                } else if (ratio.compareTo(new BigDecimal("0.6")) <= 0) {
                    return new RuleOutcome(-5, "Moderate loan-to-income ratio");
                }
                return new RuleOutcome(-25, "High loan-to-income ratio");
            },
            // Tenure rule
            request -> request.getTenureMonths() > 240
                    ? new RuleOutcome(-5, "Long repayment tenure increases exposure")
                    : new RuleOutcome(5, "Reasonable repayment tenure")
    );

    public List<RuleOutcome> evaluate(RiskAssessmentRequest request) {
        // Java 8 Stream pipeline: map each rule to its outcome
        return rules.stream()
                .map(rule -> rule.apply(request))
                .collect(Collectors.toList());
    }
}
