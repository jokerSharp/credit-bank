package io.project.calculator.service;

import io.project.calculator.model.dto.response.CreditDto;
import io.project.calculator.model.dto.request.ScoringDataDto;

public interface CalculatorService {

    CreditDto calculate(ScoringDataDto scoringDataDto);
}
