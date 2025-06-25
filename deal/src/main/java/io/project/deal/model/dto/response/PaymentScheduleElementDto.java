package io.project.deal.model.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Value
public class PaymentScheduleElementDto {
    @NotNull
    Integer number;
    @NotNull
    LocalDate date;
    @NotNull
    BigDecimal totalPayment;
    @NotNull
    BigDecimal interestPayment;
    @NotNull
    BigDecimal debtPayment;
    @NotNull
    BigDecimal remainingDebt;
}
