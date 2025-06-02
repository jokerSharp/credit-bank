package io.project.calculator.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PaymentScheduleElementDto(
        @NotNull
        Integer number,
        @NotNull
        LocalDate date,
        @NotNull
        BigDecimal totalPayment,
        @NotNull
        BigDecimal interestPayment,
        @NotNull
        BigDecimal debtPayment,
        @NotNull
        BigDecimal remainingDebt) {
}
