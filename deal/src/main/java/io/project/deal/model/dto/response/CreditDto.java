package io.project.deal.model.dto.response;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@ToString(exclude = "paymentSchedule")
@Builder
@Value
public class CreditDto {
    @NotNull
    @Min(20000)
    BigDecimal amount;
    @NotNull
    @Min(6)
    Integer term;
    @NotNull
    BigDecimal monthlyPayment;
    @NotNull
    BigDecimal rate;
    @NotNull
    BigDecimal psk;
    @NotNull
    Boolean isInsuranceEnabled;
    @NotNull
    Boolean isSalaryClient;
    @NotEmpty
    List<PaymentScheduleElementDto> paymentSchedule;
}
