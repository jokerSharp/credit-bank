package io.project.calculator.controller;

import io.project.calculator.dto.CreditDto;
import io.project.calculator.dto.LoanOfferDto;
import io.project.calculator.dto.LoanStatementRequestDto;
import io.project.calculator.dto.ScoringDataDto;

import java.util.List;

public interface CalculatorController {

    List<LoanOfferDto> offers(LoanStatementRequestDto loanStatementRequestDto);
    CreditDto calculate(ScoringDataDto scoringDataDto);
}
