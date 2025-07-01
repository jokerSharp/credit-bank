package io.project.deal.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Schema(example = """
        {
            "statementId": "3b40fb90-1d05-4e5c-8d6a-b78e12e9e9c9",
            "requestedAmount": 100000,
            "totalAmount": 161922.00,
            "term": 10,
            "monthlyPayment": 1349.35,
            "rate": 10.5,
            "isInsuranceEnabled": true,
            "isSalaryClient": true
        }
        """)
@Builder
@Value
public class LoanOfferDto {
    @NotNull
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
