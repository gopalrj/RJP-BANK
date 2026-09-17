package com.bank.esign.controller;

import com.bank.esign.dto.InitiateSignatureRequest;
import com.bank.esign.dto.SignatureResponse;
import com.bank.esign.service.SignatureService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/esign")
public class SignatureController {

    private final SignatureService signatureService;

    public SignatureController(SignatureService signatureService) {
        this.signatureService = signatureService;
    }

    @PostMapping("/initiate")
    public ResponseEntity<SignatureResponse> initiate(@Valid @RequestBody InitiateSignatureRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(signatureService.initiate(request));
    }

    @PostMapping("/{referenceCode}/complete")
    public ResponseEntity<SignatureResponse> complete(@PathVariable String referenceCode) {
        return ResponseEntity.ok(signatureService.complete(referenceCode));
    }

    @PostMapping("/{referenceCode}/decline")
    public ResponseEntity<SignatureResponse> decline(@PathVariable String referenceCode) {
        return ResponseEntity.ok(signatureService.decline(referenceCode));
    }

    @GetMapping("/{referenceCode}")
    public ResponseEntity<SignatureResponse> getByReferenceCode(@PathVariable String referenceCode) {
        return ResponseEntity.ok(signatureService.getByReferenceCode(referenceCode));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<SignatureResponse>> getByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(signatureService.getByCustomer(customerId));
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<Map<String, String>> handleErrors(RuntimeException ex) {
        Map<String, String> body = new LinkedHashMap<>();
        body.put("message", ex.getMessage());
        return ResponseEntity.badRequest().body(body);
    }
}
