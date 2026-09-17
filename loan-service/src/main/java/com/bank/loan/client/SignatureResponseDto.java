package com.bank.loan.client;

public class SignatureResponseDto {

    private Long id;
    private String referenceCode;
    private Long customerId;
    private String documentType;
    private Long relatedEntityId;
    private String status;

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
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
