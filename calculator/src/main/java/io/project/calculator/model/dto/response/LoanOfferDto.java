package io.project.calculator.model.dto.response;

import io.project.calculator.util.validation.group.NotBlankValidationGroup;
import io.project.calculator.util.validation.group.PatternValidationGroup;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

import static io.project.calculator.util.validation.MessageForRequestUtil.*;

@GroupSequence({NotBlankValidationGroup.class, PatternValidationGroup.class, LoanOfferDto.class})
@Builder
@Value
public class LoanOfferDto {
    @NotNull(message = STATEMENT_ID_EMPTY, groups = NotBlankValidationGroup.class)
    UUID statementId;
    @NotNull(message = REQUESTED_AMOUNT_EMPTY, groups = NotBlankValidationGroup.class)
    @Min(value = 20000, message = REQUESTED_AMOUNT_FORMAT, groups = PatternValidationGroup.class)
    BigDecimal requestedAmount;
    @NotNull(message = TOTAL_AMOUNT_EMPTY, groups = NotBlankValidationGroup.class)
    BigDecimal totalAmount;
    @NotNull(message = TERM_EMPTY, groups = NotBlankValidationGroup.class)
    @Min(value = 6, message = TERM_FORMAT, groups = PatternValidationGroup.class)
    Integer term;
    @NotNull(message = MONTHLY_PAYMENT_EMPTY, groups = NotBlankValidationGroup.class)
    BigDecimal monthlyPayment;
    @NotNull(message = RATE_EMPTY, groups = NotBlankValidationGroup.class)
    BigDecimal rate;
    @NotNull(message = INSURANCE_OPTION_EMPTY, groups = NotBlankValidationGroup.class)
    Boolean isInsuranceEnabled;
    @NotNull(message = SALARY_CLIENT_OPTION_EMPTY, groups = NotBlankValidationGroup.class)
    Boolean isSalaryClient;
}
