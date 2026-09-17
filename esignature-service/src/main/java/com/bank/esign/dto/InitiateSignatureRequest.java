package com.bank.esign.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class InitiateSignatureRequest {

    @NotNull
    private Long customerId;

    @NotBlank
    private String documentType;

    @NotNull
    private Long relatedEntityId;

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public String getDocumentType() { return documentType; }
    public void setDocumentType(String documentType) { this.documentType = documentType; }

    public Long getRelatedEntityId() { return relatedEntityId; }
    public void setRelatedEntityId(Long relatedEntityId) { this.relatedEntityId = relatedEntityId; }
}
