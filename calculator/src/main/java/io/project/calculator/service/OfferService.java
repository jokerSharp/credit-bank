package io.project.calculator.service;

import io.project.calculator.model.dto.response.LoanOfferDto;
import io.project.calculator.model.dto.request.LoanStatementRequestDto;

import java.math.BigDecimal;
import java.util.List;

public interface OfferService {

    List<LoanOfferDto> offers(LoanStatementRequestDto loanStatementRequestDto);
    BigDecimal adjustRateToOption(boolean isSalaryClient, boolean isInsuranceEnabled);
    BigDecimal calculateMonthlyPayment(BigDecimal requestedAmount, BigDecimal annualRate, Integer numberOfPayments);
}
