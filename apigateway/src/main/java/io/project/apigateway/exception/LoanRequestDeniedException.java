package io.project.apigateway.exception;

public class LoanRequestDeniedException extends RuntimeException {

    public LoanRequestDeniedException(String message) {
        super(message);
    }
}
