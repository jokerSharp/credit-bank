package io.project.calculator.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public record LoanStatementRequestDto(
        @NotNull
        BigDecimal amount,
        @NotNull
        Integer term,
        @NotNull
        @Size(min = 2, max = 30)
        String firstName,
        @NotNull
        @Size(min = 2, max = 30)
        String lastName,
        @Size(min = 2, max = 30)
        String middleName,
        @NotNull
        @Pattern(regexp = "^[a-z0-9A-Z_!#$%&'*+/=?`{|}~^.-]+@[a-z0-9A-Z.-]+$")
        String email,
        @NotNull
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate birthdate,
        @NotNull
        @Pattern(regexp = "^\\d{4}$")
        String passportSeries,
        @NotNull
        @Pattern(regexp = "^\\d{6}$")
        String passportNumber) {
}
