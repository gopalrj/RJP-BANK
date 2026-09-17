package com.bank.loan.rules;

import com.bank.loan.dto.LoanApplicationRequest;

import java.math.BigDecimal;
import java.util.function.Predicate;

/**
 * Pre-underwriting eligibility checks expressed as composable Java 8
 * Predicate<T> instances. Combined with Predicate.and(...) so the whole
 * gate is a single expression instead of nested if-statements.
 */
public final class EligibilityRules {

    private EligibilityRules() {
    }

    public static final Predicate<LoanApplicationRequest> MIN_CREDIT_SCORE =
            req -> req.getCreditScore() >= 500;

    public static final Predicate<LoanApplicationRequest> POSITIVE_INCOME =
            req -> req.getAnnualIncome() != null && req.getAnnualIncome().compareTo(BigDecimal.ZERO) > 0;

    public static final Predicate<LoanApplicationRequest> REASONABLE_LOAN_SIZE =
            req -> req.getAmount() != null
                    && req.getAnnualIncome() != null
                    && req.getAnnualIncome().compareTo(BigDecimal.ZERO) > 0
                    && req.getAmount().doubleValue() <= req.getAnnualIncome().doubleValue() * 5;

    public static final Predicate<LoanApplicationRequest> ACCEPTABLE_EXISTING_DEBT =
            req -> req.getExistingLoanCount() <= 6;

    // Composed pre-check gate — Java 8 Predicate combinator
    public static final Predicate<LoanApplicationRequest> IS_ELIGIBLE_FOR_UNDERWRITING =
            MIN_CREDIT_SCORE
                    .and(POSITIVE_INCOME)
                    .and(REASONABLE_LOAN_SIZE)
                    .and(ACCEPTABLE_EXISTING_DEBT);
}
