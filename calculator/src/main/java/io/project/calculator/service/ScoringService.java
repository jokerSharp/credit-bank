package io.project.calculator.service;

import io.project.calculator.model.dto.request.ScoringDataDto;

import java.math.BigDecimal;

public interface ScoringService {

    BigDecimal adjustRateToOption(boolean isSalaryClient, boolean isInsuranceEnabled);

    BigDecimal score(ScoringDataDto scoringDataDto);
}
