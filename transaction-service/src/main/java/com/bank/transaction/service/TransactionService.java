package com.bank.transaction.service;

import com.bank.transaction.dto.AccountStatement;
import com.bank.transaction.dto.TransactionRequest;
import com.bank.transaction.dto.TransactionResponse;

import java.util.List;

public interface TransactionService {

    TransactionResponse process(TransactionRequest request);

    TransactionResponse getByRef(String transactionRef);

    List<TransactionResponse> getByAccount(String accountNumber);

    AccountStatement getStatement(String accountNumber);
}
