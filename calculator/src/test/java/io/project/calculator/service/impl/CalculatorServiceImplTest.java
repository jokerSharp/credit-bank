package io.project.calculator.service.impl;

import io.project.calculator.dto.CreditDto;
import io.project.calculator.dto.LoanOfferDto;
import io.project.calculator.dto.LoanStatementRequestDto;
import io.project.calculator.dto.ScoringDataDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CalculatorServiceImplTest {

    private static final BigDecimal BASE_RATE = new BigDecimal("11.5");

    @InjectMocks
    private CalculatorServiceImpl calculatorService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(calculatorService, "baseRate", BASE_RATE);
    }

    @Test
    void offers_ShouldReturnCorrectNumberOfOffers() {
        //given
        LoanStatementRequestDto request = CalculatorServiceTestData.LOAN_STATEMENT_REQUEST_DTO;
        //when
        List<LoanOfferDto> offers = calculatorService.offers(request);
        //then
        assertEquals(4, offers.size());
    }

    @Test
    void offers_ShouldReturnOffersWithDifferentOptions() {
        //given
        LoanStatementRequestDto request = CalculatorServiceTestData.LOAN_STATEMENT_REQUEST_DTO;
        ;
        //when
        List<LoanOfferDto> offers = calculatorService.offers(request);
        //then
        LoanOfferDto baseOffer = offers.get(0);
        assertFalse(baseOffer.isSalaryClient());
        assertFalse(baseOffer.isInsuranceEnabled());
        assertEquals(BASE_RATE, baseOffer.rate());

        LoanOfferDto salaryOffer = offers.get(1);
        assertTrue(salaryOffer.isSalaryClient());
        assertFalse(salaryOffer.isInsuranceEnabled());
        assertEquals(BASE_RATE.subtract(BigDecimal.valueOf(0.4)), salaryOffer.rate());

        LoanOfferDto insuranceOffer = offers.get(2);
        assertFalse(insuranceOffer.isSalaryClient());
        assertTrue(insuranceOffer.isInsuranceEnabled());
        assertEquals(BASE_RATE.subtract(BigDecimal.valueOf(0.6)), insuranceOffer.rate());

        LoanOfferDto bestOffer = offers.get(3);
        assertTrue(bestOffer.isSalaryClient());
        assertTrue(bestOffer.isInsuranceEnabled());
        assertEquals(BASE_RATE.subtract(BigDecimal.ONE), bestOffer.rate());
    }

    @Test
    void offers_ShouldReturnCorrectRequestedAmountAndTerm() {
        //given
        LoanStatementRequestDto request = CalculatorServiceTestData.LOAN_STATEMENT_REQUEST_DTO;
        //when
        List<LoanOfferDto> offers = calculatorService.offers(request);
        //then
        for (LoanOfferDto offer : offers) {
            assertEquals(request.amount(), offer.requestedAmount());
            assertEquals(request.term(), offer.term());
        }
    }

    @Test
    void calculate_ShouldReturnCorrectFullAmountAndMonthlyPayment() {
        //given
        ScoringDataDto request = CalculatorServiceTestData.SCORING_DATA_DTO_1;
        //when
        CreditDto creditDto = calculatorService.calculate(request);
        //then
        assertEquals(BigDecimal.valueOf(144960).setScale(2, RoundingMode.HALF_UP), creditDto.psk());
        assertEquals(BigDecimal.valueOf(1208).setScale(2, RoundingMode.HALF_UP), creditDto.monthlyPayment());
    }

    @Test
    void calculate_ShouldReturnCorrectScheduleMonthlyPaymentAndLastPaymentRemainingDebt() {
        //given
        ScoringDataDto request = CalculatorServiceTestData.SCORING_DATA_DTO_2;
        //when
        CreditDto creditDto = calculatorService.calculate(request);
        //then
        assertEquals(BigDecimal.valueOf(1119.23).setScale(2, RoundingMode.HALF_UP),
                creditDto.paymentSchedule().getFirst().totalPayment());
        assertEquals(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP),
                creditDto.paymentSchedule().getLast().remainingDebt());
    }

    @Test
    void calculate_ShouldReturnCorrectRate() {
        //given
        ScoringDataDto request = CalculatorServiceTestData.SCORING_DATA_DTO_3;
        //when
        CreditDto creditDto = calculatorService.calculate(request);
        //then
        assertEquals(BigDecimal.valueOf(19.5), creditDto.rate());
    }

    @Test
    void calculate_ShouldReturnCorrectNumberOfPayments() {
        //given
        ScoringDataDto request = CalculatorServiceTestData.SCORING_DATA_DTO_1;
        //when
        CreditDto creditDto = calculatorService.calculate(request);
        //then
        assertEquals(120, creditDto.paymentSchedule().size());
    }
}