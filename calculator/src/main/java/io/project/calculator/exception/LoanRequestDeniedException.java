package io.project.calculator.exception;

public class LoanRequestDeniedException extends RuntimeException {

    public LoanRequestDeniedException(String message) {
        super(message);
    }
}
