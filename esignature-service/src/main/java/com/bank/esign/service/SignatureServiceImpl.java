package com.bank.esign.service;

import com.bank.esign.dto.InitiateSignatureRequest;
import com.bank.esign.dto.SignatureResponse;
import com.bank.esign.entity.SignatureRequest;
import com.bank.esign.entity.SignatureStatus;
import com.bank.esign.repository.SignatureRequestRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class SignatureServiceImpl implements SignatureService {

    private final SignatureRequestRepository repository;

    // Java 8 Supplier<T> functional interface for generating reference codes on demand
    private final Supplier<String> referenceCodeGenerator =
            () -> "ESIGN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

    // Java 8 Function<T,R> reused as an entity -> DTO mapper
    private final Function<SignatureRequest, SignatureResponse> toResponse = sr -> new SignatureResponse(
            sr.getId(), sr.getReferenceCode(), sr.getCustomerId(), sr.getDocumentType(),
            sr.getRelatedEntityId(), sr.getStatus(), sr.getRequestedAt(), sr.getCompletedAt(), sr.getExpiresAt());

    public SignatureServiceImpl(SignatureRequestRepository repository) {
        this.repository = repository;
    }

    @Override
    public SignatureResponse initiate(InitiateSignatureRequest request) {
        SignatureRequest entity = new SignatureRequest(
                referenceCodeGenerator.get(),
                request.getCustomerId(),
                request.getDocumentType(),
                request.getRelatedEntityId());
        return toResponse.apply(repository.save(entity));
    }

    @Override
    public SignatureResponse complete(String referenceCode) {
        SignatureRequest entity = findOrThrow(referenceCode);
        validateNotExpired(entity);
        entity.setStatus(SignatureStatus.SIGNED);
        entity.setCompletedAt(LocalDateTime.now());
        return toResponse.apply(repository.save(entity));
    }

    @Override
    public SignatureResponse decline(String referenceCode) {
        SignatureRequest entity = findOrThrow(referenceCode);
        entity.setStatus(SignatureStatus.DECLINED);
        entity.setCompletedAt(LocalDateTime.now());
        return toResponse.apply(repository.save(entity));
    }

    @Override
    public SignatureResponse getByReferenceCode(String referenceCode) {
        return toResponse.apply(findOrThrow(referenceCode));
    }

    @Override
    public List<SignatureResponse> getByCustomer(Long customerId) {
        return repository.findByCustomerId(customerId).stream()
                .map(toResponse)
                .collect(Collectors.toList());
    }

    private SignatureRequest findOrThrow(String referenceCode) {
        Optional<SignatureRequest> found = repository.findByReferenceCode(referenceCode);
        return found.orElseThrow(() ->
                new IllegalArgumentException("No signature request found for reference: " + referenceCode));
    }

    private void validateNotExpired(SignatureRequest entity) {
        if (entity.getExpiresAt() != null && entity.getExpiresAt().isBefore(LocalDateTime.now())) {
            entity.setStatus(SignatureStatus.EXPIRED);
            repository.save(entity);
            throw new IllegalStateException("Signature request has expired: " + entity.getReferenceCode());
        }
    }
}
