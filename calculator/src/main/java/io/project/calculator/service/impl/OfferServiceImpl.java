package io.project.calculator.service.impl;

import io.project.calculator.exception.LoanRequestDeniedException;
import io.project.calculator.model.dto.request.LoanStatementRequestDto;
import io.project.calculator.model.dto.response.LoanOfferDto;
import io.project.calculator.service.CalculatorService;
import io.project.calculator.service.OfferService;
import io.project.calculator.service.ScoringService;
import io.project.calculator.service.enums.LoanOfferOption;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static io.project.calculator.util.validation.ScoringMessageUtil.CLIENT_UNDERAGE_MESSAGE;

@RequiredArgsConstructor
@Slf4j
@Service
public class OfferServiceImpl implements OfferService {

    private static final Integer BASE_PERIODS_AMOUNT_PER_YEAR = 12;

    private final ScoringService scoringService;
    private final CalculatorService calculatorService;

    @Override
    public List<LoanOfferDto> offers(LoanStatementRequestDto loanStatementRequestDto) {
        log.info("received LoanStatementRequestDto object={}", loanStatementRequestDto);
        int age = Period.between(loanStatementRequestDto.getBirthdate(), LocalDate.now()).getYears();
        if (age < 18) {
            throw new LoanRequestDeniedException(CLIENT_UNDERAGE_MESSAGE);
        }
        int numberOfPayments = loanStatementRequestDto.getTerm() * BASE_PERIODS_AMOUNT_PER_YEAR;
        List<LoanOfferDto> offers = new ArrayList<>();
        for (LoanOfferOption option : LoanOfferOption.values()) {
            BigDecimal adjustedRate = scoringService
                    .adjustRateToOption(option.isSalaryClient(), option.isInsuranceEnabled());
            BigDecimal monthlyPayment = calculatorService
                    .calculateMonthlyPayment(loanStatementRequestDto.getAmount(), adjustedRate, numberOfPayments);
            BigDecimal psk = monthlyPayment.multiply(BigDecimal.valueOf(numberOfPayments));
            offers.add(LoanOfferDto.builder()
                    .requestedAmount(loanStatementRequestDto.getAmount())
                    .totalAmount(psk)
                    .term(loanStatementRequestDto.getTerm())
                    .monthlyPayment(monthlyPayment)
                    .rate(adjustedRate)
                    .isInsuranceEnabled(option.isInsuranceEnabled())
                    .isSalaryClient(option.isSalaryClient())
                    .build());
        }
        log.info("returning LoanOfferDtos={}", offers);
        return offers;
    }
}
