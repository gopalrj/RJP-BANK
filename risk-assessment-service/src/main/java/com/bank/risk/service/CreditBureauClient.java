package com.bank.risk.service;

import com.bank.risk.model.CreditBureauResponse;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Integration point for the third-party credit bureau / underwriting API.
 * In production this would issue a RestTemplate/WebClient call to the
 * bureau's HTTPS endpoint (URL + API key sourced from application.yml);
 * it is simulated here so the module is runnable without external
 * network access, but the calling code (RiskAssessmentServiceImpl)
 * treats it exactly like a network call — including running it inside
 * a CompletableFuture.
 */
@Component
public class CreditBureauClient {

    public CreditBureauResponse fetchBureauReport(Long customerId, int selfReportedScore) {
        // Simulated latency + external bureau scoring logic
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        int variance = ThreadLocalRandom.current().nextInt(-20, 21);
        int bureauScore = Math.min(900, Math.max(300, selfReportedScore + variance));
        boolean defaulter = bureauScore < 500 && ThreadLocalRandom.current().nextInt(10) < 2;
        int activeLines = ThreadLocalRandom.current().nextInt(0, 5);
        return new CreditBureauResponse(bureauScore, defaulter, activeLines);
    }
}
