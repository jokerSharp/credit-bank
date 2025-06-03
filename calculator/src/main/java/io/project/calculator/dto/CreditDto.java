package io.project.calculator.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record CreditDto(
        @NotNull
        BigDecimal amount,
        @NotNull
        Integer term,
        @NotNull
        BigDecimal monthlyPayment,
        @NotNull
        BigDecimal rate,
        @NotNull
        BigDecimal psk,
        @NotNull
        Boolean isInsuranceEnabled,
        @NotNull
        Boolean isSalaryClient,
        @NotEmpty
        List<PaymentScheduleElementDto> paymentSchedule) {
}
