package io.project.deal.exception;

public class LoanRequestDeniedException extends RuntimeException {

    public LoanRequestDeniedException(String message) {
        super(message);
    }
}
