package io.project.calculator.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
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
}
