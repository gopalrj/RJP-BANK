package com.bank.risk.service;

import com.bank.risk.model.RiskAssessmentRequest;
import com.bank.risk.model.RiskAssessmentResponse;

public interface RiskAssessmentService {
    RiskAssessmentResponse assess(RiskAssessmentRequest request);
}
