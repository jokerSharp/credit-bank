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
            case UNEMPLOYED -> throw new LoanRequestDeniedException("Client should have a work");
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
            throw new LoanRequestDeniedException("Requested amount should be less than 24 client salaries");
        }

        resultRate = switch (scoringDataDto.getMaritalStatus()) {
            case MARRIED -> resultRate.subtract(threePercent);
            case DIVORCED -> resultRate.add(BigDecimal.ONE);
            case SINGLE -> resultRate;
        };

        int age = Period.between(scoringDataDto.getBirthdate(), LocalDate.now()).getYears();

        if (age < 20 || age > 65) {
            throw new LoanRequestDeniedException("Client age should be between 20 and 65");
        }

        resultRate = switch (scoringDataDto.getGender()) {
            case MALE -> age >= 30 && age <= 55 ? resultRate.subtract(threePercent) : resultRate;
            case FEMALE -> age >= 32 && age <= 60 ? resultRate.subtract(threePercent) : resultRate;
            case NON_BINARY -> resultRate.add(BigDecimal.valueOf(7));
        };

        if (employment.getWorkExperienceTotal() < 18) {
            throw new LoanRequestDeniedException("Client total work experience should at least 18 months");
        }

        if (employment.getWorkExperienceCurrent() < 3) {
            throw new LoanRequestDeniedException("Client current work experience should at least 3 months");
        }

        log.debug("Result rate={}", resultRate);
        return resultRate;
    }
}
