package com.bank.loan.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Declarative REST client to the risk-assessment-service, resolved through
 * Eureka by logical service name ("risk-assessment-service") rather than a
 * hardcoded host:port — the essence of inter-service calls in a
 * microservices architecture.
 */
@FeignClient(name = "risk-assessment-service")
public interface RiskAssessmentClient {

    @PostMapping("/api/risk/assess")
    RiskAssessmentResponseDto assess(@RequestBody RiskAssessmentRequestDto request);
}
