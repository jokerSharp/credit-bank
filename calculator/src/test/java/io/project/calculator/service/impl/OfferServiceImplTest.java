package io.project.calculator.service.impl;

import io.project.calculator.data.CalculatorTestData;
import io.project.calculator.exception.LoanRequestDeniedException;
import io.project.calculator.model.dto.request.LoanStatementRequestDto;
import io.project.calculator.model.dto.response.LoanOfferDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OfferServiceImplTest {

    private static final BigDecimal BASE_RATE = new BigDecimal("11.5");

    @InjectMocks
    private ScoringServiceImpl scoringService;

    @InjectMocks
    private OfferServiceImpl offerService;

    @InjectMocks
    private CalculatorServiceImpl calculatorService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(scoringService, "baseRate", BASE_RATE);
        ReflectionTestUtils.setField(offerService, "scoringService", scoringService);
        ReflectionTestUtils.setField(offerService, "calculatorService", calculatorService);
    }

    @Test
    void offers_ShouldReturnCorrectNumberOfOffers() {
        //given
        LoanStatementRequestDto request = CalculatorTestData.VALID_LOAN_STATEMENT_REQUEST_DTO;
        //when
        List<LoanOfferDto> offers = offerService.offers(request);
        //then
        assertEquals(4, offers.size());
    }

    @Test
    void offers_ShouldReturnOffersWithDifferentOptions() {
        //given
        LoanStatementRequestDto request = CalculatorTestData.VALID_LOAN_STATEMENT_REQUEST_DTO;
        ;
        //when
        List<LoanOfferDto> offers = offerService.offers(request);
        //then
        LoanOfferDto baseOffer = offers.get(0);
        assertFalse(baseOffer.getIsSalaryClient());
        assertFalse(baseOffer.getIsInsuranceEnabled());
        assertEquals(BASE_RATE, baseOffer.getRate());

        LoanOfferDto salaryOffer = offers.get(1);
        assertTrue(salaryOffer.getIsSalaryClient());
        assertFalse(salaryOffer.getIsInsuranceEnabled());
        assertEquals(BASE_RATE.subtract(BigDecimal.valueOf(0.4)), salaryOffer.getRate());

        LoanOfferDto insuranceOffer = offers.get(2);
        assertFalse(insuranceOffer.getIsSalaryClient());
        assertTrue(insuranceOffer.getIsInsuranceEnabled());
        assertEquals(BASE_RATE.subtract(BigDecimal.valueOf(0.6)), insuranceOffer.getRate());

        LoanOfferDto bestOffer = offers.get(3);
        assertTrue(bestOffer.getIsSalaryClient());
        assertTrue(bestOffer.getIsInsuranceEnabled());
        assertEquals(BASE_RATE.subtract(BigDecimal.ONE), bestOffer.getRate());
    }

    @Test
    void offers_ShouldReturnCorrectRequestedAmountAndTerm() {
        //given
        LoanStatementRequestDto request = CalculatorTestData.VALID_LOAN_STATEMENT_REQUEST_DTO;
        //when
        List<LoanOfferDto> offers = offerService.offers(request);
        //then
        for (LoanOfferDto offer : offers) {
            assertEquals(request.getAmount(), offer.getRequestedAmount());
            assertEquals(request.getTerm(), offer.getTerm());
        }
    }

    @Test
    void offers_LoanRequestShouldBeDeclinedIfClientIsYounger18() {
        //given
        LoanStatementRequestDto request = CalculatorTestData.INVALID_LOAN_STATEMENT_REQUEST_DTO;
        //then
        assertThrows(LoanRequestDeniedException.class, () -> offerService.offers(request));
    }

}