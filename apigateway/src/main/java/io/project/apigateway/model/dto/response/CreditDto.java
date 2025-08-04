package io.project.apigateway.model.dto.response;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@ToString(exclude = "paymentSchedule")
@Builder
@Value
public class CreditDto {
    BigDecimal amount;
    Integer term;
    BigDecimal monthlyPayment;
    BigDecimal rate;
    BigDecimal psk;
    Boolean isInsuranceEnabled;
    Boolean isSalaryClient;
    List<PaymentScheduleElementDto> paymentSchedule;
}
