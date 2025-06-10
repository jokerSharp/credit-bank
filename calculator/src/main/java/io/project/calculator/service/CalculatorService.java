package io.project.calculator.service;

import io.project.calculator.model.dto.request.ScoringDataDto;
import io.project.calculator.model.dto.response.CreditDto;

import java.math.BigDecimal;

public interface CalculatorService {

    BigDecimal calculateMonthlyPayment(BigDecimal requestedAmount, BigDecimal annualRate, Integer numberOfPayments);

    CreditDto calculate(ScoringDataDto scoringDataDto);
}
