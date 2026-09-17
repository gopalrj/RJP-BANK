package com.bank.loan.client;

public class InitiateSignatureRequestDto {

    private Long customerId;
    private String documentType;
    private Long relatedEntityId;

    public InitiateSignatureRequestDto() {
    }

    public InitiateSignatureRequestDto(Long customerId, String documentType, Long relatedEntityId) {
        this.customerId = customerId;
        this.documentType = documentType;
        this.relatedEntityId = relatedEntityId;
    }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    public String getDocumentType() { return documentType; }
    public void setDocumentType(String documentType) { this.documentType = documentType; }
    public Long getRelatedEntityId() { return relatedEntityId; }
    public void setRelatedEntityId(Long relatedEntityId) { this.relatedEntityId = relatedEntityId; }
}
