package com.bank.loan.exception;

public class LoanIneligibleException extends RuntimeException {
    public LoanIneligibleException(String message) {
        super(message);
    }
}
