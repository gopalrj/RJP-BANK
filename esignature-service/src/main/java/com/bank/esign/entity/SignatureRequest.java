package com.bank.esign.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "signature_requests")
public class SignatureRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String referenceCode;

    @Column(nullable = false)
    private Long customerId;

    @Column(nullable = false)
    private String documentType;

    @Column(nullable = false)
    private Long relatedEntityId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SignatureStatus status;

    @Column(nullable = false)
    private LocalDateTime requestedAt;

    private LocalDateTime completedAt;

    private LocalDateTime expiresAt;

    public SignatureRequest() {
    }

    public SignatureRequest(String referenceCode, Long customerId, String documentType, Long relatedEntityId) {
        this.referenceCode = referenceCode;
        this.customerId = customerId;
        this.documentType = documentType;
        this.relatedEntityId = relatedEntityId;
        this.status = SignatureStatus.INITIATED;
        this.requestedAt = LocalDateTime.now();
        this.expiresAt = requestedAt.plusDays(3);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getReferenceCode() { return referenceCode; }
    public void setReferenceCode(String referenceCode) { this.referenceCode = referenceCode; }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public String getDocumentType() { return documentType; }
    public void setDocumentType(String documentType) { this.documentType = documentType; }

    public Long getRelatedEntityId() { return relatedEntityId; }
    public void setRelatedEntityId(Long relatedEntityId) { this.relatedEntityId = relatedEntityId; }

    public SignatureStatus getStatus() { return status; }
    public void setStatus(SignatureStatus status) { this.status = status; }

    public LocalDateTime getRequestedAt() { return requestedAt; }
    public void setRequestedAt(LocalDateTime requestedAt) { this.requestedAt = requestedAt; }

    public LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }

    public LocalDateTime getExpiresAt() { return expiresAt; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }
}
