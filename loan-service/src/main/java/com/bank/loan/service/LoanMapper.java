package com.bank.loan.service;

import com.bank.loan.dto.LoanApplicationResponse;
import com.bank.loan.entity.LoanApplication;

import java.util.function.Function;

public final class LoanMapper {

    private LoanMapper() {
    }

    public static final Function<LoanApplication, LoanApplicationResponse> TO_RESPONSE = loan ->
            new LoanApplicationResponse(
                    loan.getId(), loan.getCustomerId(), loan.getAmount(), loan.getTenureMonths(),
                    loan.getPurpose(), loan.getStatus(), loan.getAppliedDate(), loan.getDecisionDate(),
                    loan.getRiskScore(), loan.getRiskLevel(), loan.getSignatureReference(), loan.getRemarks());
}
