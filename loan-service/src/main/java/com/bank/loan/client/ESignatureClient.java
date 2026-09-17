package com.bank.loan.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "esignature-service")
public interface ESignatureClient {

    @PostMapping("/api/esign/initiate")
    SignatureResponseDto initiate(@RequestBody InitiateSignatureRequestDto request);
}
