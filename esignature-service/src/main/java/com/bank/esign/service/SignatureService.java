package com.bank.esign.service;

import com.bank.esign.dto.InitiateSignatureRequest;
import com.bank.esign.dto.SignatureResponse;

import java.util.List;

public interface SignatureService {
    SignatureResponse initiate(InitiateSignatureRequest request);
    SignatureResponse complete(String referenceCode);
    SignatureResponse decline(String referenceCode);
    SignatureResponse getByReferenceCode(String referenceCode);
    List<SignatureResponse> getByCustomer(Long customerId);
}
