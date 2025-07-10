package io.project.statement.exception;

public class LoanRequestDeniedException extends RuntimeException {

    public LoanRequestDeniedException(String message) {
        super(message);
    }
}
