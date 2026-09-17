package com.bank.loan.service;

import com.bank.loan.client.*;
import com.bank.loan.dto.LoanApplicationRequest;
import com.bank.loan.dto.LoanApplicationResponse;
import com.bank.loan.dto.LoanPortfolioSummary;
import com.bank.loan.entity.LoanApplication;
import com.bank.loan.entity.LoanStatus;
import com.bank.loan.exception.LoanIneligibleException;
import com.bank.loan.exception.LoanNotFoundException;
import com.bank.loan.repository.LoanApplicationRepository;
import com.bank.loan.rules.EligibilityRules;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class LoanApplicationServiceImpl implements LoanApplicationService {

    private final LoanApplicationRepository loanRepository;
    private final RiskAssessmentClient riskAssessmentClient;
    private final ESignatureClient eSignatureClient;

    public LoanApplicationServiceImpl(LoanApplicationRepository loanRepository,
                                       RiskAssessmentClient riskAssessmentClient,
                                       ESignatureClient eSignatureClient) {
        this.loanRepository = loanRepository;
        this.riskAssessmentClient = riskAssessmentClient;
        this.eSignatureClient = eSignatureClient;
    }

    @Override
    public LoanApplicationResponse apply(LoanApplicationRequest request) {
        // Java 8 Predicate gate: fail fast before ever calling the risk-assessment service
        if (!EligibilityRules.IS_ELIGIBLE_FOR_UNDERWRITING.test(request)) {
            throw new LoanIneligibleException(
                    "Application does not meet minimum eligibility criteria for underwriting");
        }

        LoanApplication loan = new LoanApplication(
                request.getCustomerId(), request.getAmount(), request.getTenureMonths(), request.getPurpose());
        loan.setStatus(LoanStatus.UNDER_REVIEW);
        loan = loanRepository.save(loan);

        // Java 8 CompletableFuture: call the downstream risk-assessment microservice
        // asynchronously so the calling thread isn't blocked on network I/O.
        CompletableFuture<RiskAssessmentResponseDto> riskFuture = CompletableFuture.supplyAsync(() ->
                riskAssessmentClient.assess(new RiskAssessmentRequestDto(
                        request.getCustomerId(), request.getAmount(), request.getTenureMonths(),
                        request.getAnnualIncome(), request.getExistingLoanCount(), request.getCreditScore())));

        RiskAssessmentResponseDto riskResult = riskFuture.join();

        loan.setRiskScore(riskResult.getRiskScore());
        loan.setRiskLevel(riskResult.getRiskLevel());
        // Java 8 Stream: join remarks list into a single readable string
        loan.setRemarks(riskResult.getRemarks() == null ? "" :
                riskResult.getRemarks().stream().collect(Collectors.joining("; ")));
        loan.setDecisionDate(LocalDateTime.now());

        if (riskResult.isApproved()) {
            loan.setStatus(LoanStatus.APPROVED);
            loan = loanRepository.save(loan);

            // Once approved, asynchronously kick off the e-signature workflow
            LoanApplication finalLoan = loan;
            CompletableFuture<SignatureResponseDto> signatureFuture = CompletableFuture.supplyAsync(() ->
                    eSignatureClient.initiate(new InitiateSignatureRequestDto(
                            request.getCustomerId(), "LOAN_AGREEMENT", finalLoan.getId())));

            SignatureResponseDto signature = signatureFuture.join();
            loan.setSignatureReference(signature.getReferenceCode());
            loan.setStatus(LoanStatus.AWAITING_SIGNATURE);
        } else {
            loan.setStatus(LoanStatus.REJECTED);
        }

        return LoanMapper.TO_RESPONSE.apply(loanRepository.save(loan));
    }

    @Override
    public LoanApplicationResponse getById(Long id) {
        return loanRepository.findById(id)
                .map(LoanMapper.TO_RESPONSE)
                .orElseThrow(() -> new LoanNotFoundException("Loan application not found with id: " + id));
    }

    @Override
    public List<LoanApplicationResponse> getByCustomer(Long customerId) {
        return loanRepository.findByCustomerId(customerId).stream()
                .map(LoanMapper.TO_RESPONSE)
                .collect(Collectors.toList());
    }

    @Override
    public LoanPortfolioSummary getPortfolioSummary() {
        List<LoanApplication> all = loanRepository.findAll();

        // Java 8 Collectors: summing, averaging, max and grouping — all in one pass each
        BigDecimal totalAmount = all.stream()
                .map(LoanApplication::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        double averageAmount = all.stream()
                .mapToDouble(loan -> loan.getAmount().doubleValue())
                .average()
                .orElse(0.0);

        BigDecimal maxAmount = all.stream()
                .map(LoanApplication::getAmount)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        Map<String, Long> countByStatus = all.stream()
                .collect(Collectors.groupingBy(loan -> loan.getStatus().name(), Collectors.counting()));

        return new LoanPortfolioSummary(all.size(), totalAmount, averageAmount, maxAmount, countByStatus);
    }
}
