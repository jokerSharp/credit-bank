package io.project.calculator.service.impl;

import io.project.calculator.exception.LoanRequestDeniedException;
import io.project.calculator.model.dto.request.EmploymentDto;
import io.project.calculator.model.dto.request.ScoringDataDto;
import io.project.calculator.service.ScoringService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

import static io.project.calculator.util.validation.ScoringMessageUtil.*;

@Slf4j
@Service
public class ScoringServiceImpl implements ScoringService {

    @Value("${app.base-rate}")
    private BigDecimal baseRate;

    public BigDecimal adjustRateToOption(boolean isSalaryClient, boolean isInsuranceEnabled) {
        BigDecimal resultRate = baseRate;
        if (isSalaryClient) {
            resultRate = resultRate.subtract(BigDecimal.valueOf(0.4));
        }
        if (isInsuranceEnabled) {
            resultRate = resultRate.subtract(BigDecimal.valueOf(0.6));
        }
        return resultRate;
    }

    @Override
    public BigDecimal score(ScoringDataDto scoringDataDto) {
        BigDecimal resultRate = adjustRateToOption(scoringDataDto.getIsSalaryClient(),
                scoringDataDto.getIsInsuranceEnabled());
        BigDecimal threePercent = BigDecimal.valueOf(3);
        EmploymentDto employment = scoringDataDto.getEmployment();

        resultRate = switch (employment.getEmploymentStatus()) {
            case UNEMPLOYED -> throw new LoanRequestDeniedException(CLIENT_HAS_NO_WORK_MESSAGE);
            case EMPLOYED -> resultRate;
            case SELF_EMPLOYED -> resultRate.add(BigDecimal.TWO);
            case BUSINESS_OWNER -> resultRate.add(BigDecimal.ONE);
        };

        resultRate = switch (employment.getPosition()) {
            case TOP_MANAGER -> resultRate.subtract(threePercent);
            case MIDDLE_MANAGER -> resultRate.subtract(BigDecimal.ONE);
            case LINEAR_EMPLOYEE -> resultRate;
        };

        BigDecimal hugeRequestedAmount = employment.getSalary().multiply(BigDecimal.valueOf(24));
        if (scoringDataDto.getAmount().compareTo(hugeRequestedAmount) > 0) {
            throw new LoanRequestDeniedException(HIGH_REQUESTED_AMOUNT_MESSAGE);
        }

        resultRate = switch (scoringDataDto.getMaritalStatus()) {
            case MARRIED -> resultRate.subtract(threePercent);
            case DIVORCED -> resultRate.add(BigDecimal.ONE);
            case SINGLE -> resultRate;
        };

        int age = Period.between(scoringDataDto.getBirthdate(), LocalDate.now()).getYears();

        if (age < 20 || age > 65) {
            throw new LoanRequestDeniedException(INVALID_CLIENT_AGE_MESSAGE);
        }

        resultRate = switch (scoringDataDto.getGender()) {
            case MALE -> age >= 30 && age <= 55 ? resultRate.subtract(threePercent) : resultRate;
            case FEMALE -> age >= 32 && age <= 60 ? resultRate.subtract(threePercent) : resultRate;
            case NON_BINARY -> resultRate.add(BigDecimal.valueOf(7));
        };

        if (employment.getWorkExperienceTotal() < 18) {
            throw new LoanRequestDeniedException(CLIENT_TOTAL_WORK_EXPERIENCE_MESSAGE);
        }

        if (employment.getWorkExperienceCurrent() < 3) {
            throw new LoanRequestDeniedException(CLIENT_CURRENT_WORK_EXPERIENCE_MESSAGE);
        }

        log.debug("Result rate={}", resultRate);
        return resultRate;
    }
}
