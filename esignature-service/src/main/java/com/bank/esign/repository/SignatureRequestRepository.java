package com.bank.esign.repository;

import com.bank.esign.entity.SignatureRequest;
import com.bank.esign.entity.SignatureStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SignatureRequestRepository extends JpaRepository<SignatureRequest, Long> {

    Optional<SignatureRequest> findByReferenceCode(String referenceCode);

    List<SignatureRequest> findByCustomerId(Long customerId);

    List<SignatureRequest> findByStatus(SignatureStatus status);
}
