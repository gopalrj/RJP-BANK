package com.bank.risk.rules;

import com.bank.risk.model.RiskAssessmentRequest;

import java.util.function.Function;

/**
 * A single underwriting rule: given the loan request, contributes a
 * (possibly negative) adjustment to the overall risk score, plus an
 * optional remark. Modeled as a Java 8 functional interface so the
 * whole rule set can be expressed as a List<UnderwritingRule> and
 * folded with Stream.reduce(...) — see RuleEngine.
 */
@FunctionalInterface
public interface UnderwritingRule extends Function<RiskAssessmentRequest, RuleOutcome> {
}
