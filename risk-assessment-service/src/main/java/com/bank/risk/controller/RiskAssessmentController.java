package com.bank.risk.controller;

import com.bank.risk.model.RiskAssessmentRequest;
import com.bank.risk.model.RiskAssessmentResponse;
import com.bank.risk.service.RiskAssessmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/risk")
public class RiskAssessmentController {

    private final RiskAssessmentService riskAssessmentService;

    public RiskAssessmentController(RiskAssessmentService riskAssessmentService) {
        this.riskAssessmentService = riskAssessmentService;
    }

    @PostMapping("/assess")
    public ResponseEntity<RiskAssessmentResponse> assess(@Valid @RequestBody RiskAssessmentRequest request) {
        return ResponseEntity.ok(riskAssessmentService.assess(request));
    }
}
