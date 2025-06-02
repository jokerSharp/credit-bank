package io.project.calculator.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record LoanOfferDto(
        @NotNull
        UUID statementId,
        @NotNull
        @Min(20000)
        BigDecimal requestedAmount,
        @NotNull
        BigDecimal totalAmount,
        @NotNull
        @Min(6)
        Integer term,
        @NotNull
        BigDecimal monthlyPayment,
        @NotNull
        BigDecimal rate,
        @NotNull
        Boolean isInsuranceEnabled,
        @NotNull
        Boolean isSalaryClient) {
}
