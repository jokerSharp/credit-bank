package io.project.calculator.model.dto.response;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Value
public class LoanOfferDto {
    UUID statementId;
    @NotNull
    @Min(20000)
    BigDecimal requestedAmount;
    @NotNull
    BigDecimal totalAmount;
    @NotNull
    @Min(6)
    Integer term;
    @NotNull
    BigDecimal monthlyPayment;
    @NotNull
    BigDecimal rate;
    @NotNull
    Boolean isInsuranceEnabled;
    @NotNull
    Boolean isSalaryClient;
}
