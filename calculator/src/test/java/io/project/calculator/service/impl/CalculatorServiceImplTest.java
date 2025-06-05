package io.project.calculator.service.impl;

import io.project.calculator.data.CalculatorTestData;
import io.project.calculator.model.dto.request.ScoringDataDto;
import io.project.calculator.model.dto.response.CreditDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CalculatorServiceImplTest {

    private static final BigDecimal BASE_RATE = new BigDecimal("11.5");

    @InjectMocks
    private OfferServiceImpl offerService;

    @InjectMocks
    private CalculatorServiceImpl calculatorService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(offerService, "baseRate", BASE_RATE);
        ReflectionTestUtils.setField(calculatorService, "offerService", offerService);
    }

    @Test
    void calculate_ShouldReturnCorrectFullAmountAndMonthlyPayment() {
        //given
        ScoringDataDto request = CalculatorTestData.SCORING_DATA_DTO_1;
        //when
        CreditDto creditDto = calculatorService.calculate(request);
        //then
        assertEquals(BigDecimal.valueOf(144960).setScale(2, RoundingMode.HALF_UP), creditDto.getPsk());
        assertEquals(BigDecimal.valueOf(1208).setScale(2, RoundingMode.HALF_UP), creditDto.getMonthlyPayment());
    }

    @Test
    void calculate_ShouldReturnCorrectScheduleMonthlyPaymentAndLastPaymentRemainingDebt() {
        //given
        ScoringDataDto request = CalculatorTestData.SCORING_DATA_DTO_2;
        //when
        CreditDto creditDto = calculatorService.calculate(request);
        //then
        assertEquals(BigDecimal.valueOf(1119.23).setScale(2, RoundingMode.HALF_UP),
                creditDto.getPaymentSchedule().getFirst().getTotalPayment());
        assertEquals(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP),
                creditDto.getPaymentSchedule().getLast().getRemainingDebt());
    }

    @Test
    void calculate_ShouldReturnCorrectRate() {
        //given
        ScoringDataDto request = CalculatorTestData.SCORING_DATA_DTO_3;
        //when
        CreditDto creditDto = calculatorService.calculate(request);
        //then
        assertEquals(BigDecimal.valueOf(19.5), creditDto.getRate());
    }

    @Test
    void calculate_ShouldReturnCorrectNumberOfPayments() {
        //given
        ScoringDataDto request = CalculatorTestData.SCORING_DATA_DTO_1;
        //when
        CreditDto creditDto = calculatorService.calculate(request);
        //then
        assertEquals(120, creditDto.getPaymentSchedule().size());
    }
}