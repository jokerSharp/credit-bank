package io.project.deal.exception;

import feign.RetryableException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
        log.error(e.getMessage());
        List<ErrorResponse.CustomFieldError> errors = e.getBindingResult().getFieldErrors().stream()
                .map(error -> new ErrorResponse.CustomFieldError(
                        error.getField(),
                        error.getRejectedValue(),
                        error.getDefaultMessage()))
                .toList();
        return ResponseEntity.badRequest().body(new ErrorResponse(LocalDateTime.now(), errors));
    }

    @ExceptionHandler(LoanRequestDeniedException.class)
    public ResponseEntity<ErrorResponse> handleLoanRequestDeniedException(LoanRequestDeniedException e) {
        log.error(e.getMessage());
        return ResponseEntity.unprocessableEntity()
                .body(new ErrorResponse(LocalDateTime.now(),
                        Collections.singletonList(ErrorResponse.CustomFieldError.builder().message(e.getMessage())
                                .build())));
    }

    @ExceptionHandler(RetryableException.class)
    public ResponseEntity<ErrorResponse> handleRetryableException(RetryableException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(new ErrorResponse(LocalDateTime.now(),
                        Collections.singletonList(ErrorResponse.CustomFieldError.builder().message(e.getMessage())
                                .build())));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(EntityNotFoundException e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(new ErrorResponse(LocalDateTime.now(),
                Collections.singletonList(ErrorResponse.CustomFieldError.builder().message(e.getMessage()).build())));
    }
}
