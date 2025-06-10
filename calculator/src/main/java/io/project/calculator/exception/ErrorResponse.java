package io.project.calculator.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
public class ErrorResponse {

    private LocalDateTime timestamp;
    private List<CustomFieldError> errors;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @AllArgsConstructor
    @Getter
    public static class CustomFieldError {
        private String field;
        private Object rejectedValue;
        private String message;
    }
}
