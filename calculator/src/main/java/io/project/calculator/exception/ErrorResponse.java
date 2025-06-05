package io.project.calculator.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
public class ErrorResponse {

    private LocalDateTime timestamp;
    private List<CustomFieldError> fieldErrors;

    @AllArgsConstructor
    @Getter
    public static class CustomFieldError {
        private String field;
        private Object rejectedValue;
        private String message;
    }
}
