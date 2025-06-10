package io.project.calculator.service.impl;

import io.project.calculator.data.CalculatorTestData;
import io.project.calculator.exception.LoanRequestDeniedException;
import io.project.calculator.model.dto.request.ScoringDataDto;
import io.project.calculator.service.enums.LoanOfferOption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ScoringServiceImplTest {

    private static final BigDecimal BASE_RATE = new BigDecimal("11.5");

    @InjectMocks
    private ScoringServiceImpl scoringService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(scoringService, "baseRate", BASE_RATE);
    }

    @Test
    void adjustRateToOption_ShouldReturnCorrectRate() {
        //given
        LoanOfferOption option = LoanOfferOption.HAVE_INSURANCE_SALARY_CLIENT;
        //when
        BigDecimal rate = scoringService.adjustRateToOption(option.isSalaryClient(), option.isInsuranceEnabled());
        //then
        assertEquals(BigDecimal.valueOf(10.5), rate);
    }

    @Test
    void score_ShouldReturnCorrectRate() {
        //given
        ScoringDataDto request = CalculatorTestData.SCORING_DATA_DTO_1;
        //when
        BigDecimal rate = scoringService.score(request);
        //then
        assertEquals(BigDecimal.valueOf(7.9), rate);
    }

    @Test
    void score_RequestShouldBeDeclinedIfRequestedAmountMoreThan24Salaries() {
        //given
        ScoringDataDto request = CalculatorTestData.HIGH_AMOUNT_SCORING_DATA_DTO;
        //then
        assertThrows(LoanRequestDeniedException.class, () -> scoringService.score(request));
    }

    @Test
    void score_RequestShouldBeDeclinedIfInsufficientTotalExperience() {
        //given
        ScoringDataDto request = CalculatorTestData.INSUFFICIENT_TOTAL_EXPERIENCE_SCORING_DATA_DTO;
        //then
        assertThrows(LoanRequestDeniedException.class, () -> scoringService.score(request));
    }

    @Test
    void score_RequestShouldBeDeclinedIfInsufficientCurrentExperience() {
        //given
        ScoringDataDto request = CalculatorTestData.INSUFFICIENT_CURRENT_EXPERIENCE_SCORING_DATA_DTO;
        //then
        assertThrows(LoanRequestDeniedException.class, () -> scoringService.score(request));
    }

    @Test
    void score_RequestShouldBeDeclinedForUnemployed() {
        //given
        ScoringDataDto request = CalculatorTestData.UNEMPLOYED_SCORING_DATA_DTO;
        //then
        assertThrows(LoanRequestDeniedException.class, () -> scoringService.score(request));
    }

    @Test
    void score_RequestShouldBeDeclinedIfClientIsTooYoung() {
        //given
        ScoringDataDto request = CalculatorTestData.TOO_YOUNG_SCORING_DATA_DTO;
        //then
        assertThrows(LoanRequestDeniedException.class, () -> scoringService.score(request));
    }

    @Test
    void score_RequestShouldBeDeclinedIfClientIsTooOld() {
        //given
        ScoringDataDto request = CalculatorTestData.TOO_OLD_SCORING_DATA_DTO;
        //then
        assertThrows(LoanRequestDeniedException.class, () -> scoringService.score(request));
    }
}