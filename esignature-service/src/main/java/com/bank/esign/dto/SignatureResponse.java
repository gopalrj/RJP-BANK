package com.bank.esign.dto;

import com.bank.esign.entity.SignatureStatus;

import java.time.LocalDateTime;

public class SignatureResponse {

    private Long id;
    private String referenceCode;
    private Long customerId;
    private String documentType;
    private Long relatedEntityId;
    private SignatureStatus status;
    private LocalDateTime requestedAt;
    private LocalDateTime completedAt;
    private LocalDateTime expiresAt;

    public SignatureResponse() {
    }

    public SignatureResponse(Long id, String referenceCode, Long customerId, String documentType,
                              Long relatedEntityId, SignatureStatus status, LocalDateTime requestedAt,
                              LocalDateTime completedAt, LocalDateTime expiresAt) {
        this.id = id;
        this.referenceCode = referenceCode;
        this.customerId = customerId;
        this.documentType = documentType;
        this.relatedEntityId = relatedEntityId;
        this.status = status;
        this.requestedAt = requestedAt;
        this.completedAt = completedAt;
        this.expiresAt = expiresAt;
    }

    public Long getId() { return id; }
    public String getReferenceCode() { return referenceCode; }
    public Long getCustomerId() { return customerId; }
    public String getDocumentType() { return documentType; }
    public Long getRelatedEntityId() { return relatedEntityId; }
    public SignatureStatus getStatus() { return status; }
    public LocalDateTime getRequestedAt() { return requestedAt; }
    public LocalDateTime getCompletedAt() { return completedAt; }
    public LocalDateTime getExpiresAt() { return expiresAt; }
}
