package io.project.calculator.service.impl;

import io.project.calculator.model.dto.request.LoanStatementRequestDto;
import io.project.calculator.model.dto.response.LoanOfferDto;
import io.project.calculator.service.enums.LoanOfferOption;
import io.project.calculator.service.OfferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class OfferServiceImpl implements OfferService {

    @Value("${app.base-rate}")
    private BigDecimal baseRate;

    @Override
    public List<LoanOfferDto> offers(LoanStatementRequestDto loanStatementRequestDto) {
        log.info("received LoanStatementRequestDto object={}", loanStatementRequestDto);
        int numberOfPayments = loanStatementRequestDto.getTerm() * 12;
        List<LoanOfferDto> offers = new ArrayList<>();
        for (LoanOfferOption option : LoanOfferOption.values()) {
            BigDecimal adjustedRate = adjustRateToOption(option.isSalaryClient(), option.isInsuranceEnabled());
            BigDecimal monthlyPayment = calculateMonthlyPayment(loanStatementRequestDto.getAmount(), adjustedRate,
                    numberOfPayments);
            BigDecimal psk = monthlyPayment.multiply(BigDecimal.valueOf(numberOfPayments));
            offers.add(LoanOfferDto.builder()
                    .statementId(UUID.randomUUID())
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

    public BigDecimal calculateMonthlyPayment(BigDecimal requestedAmount,
                                              BigDecimal annualRate,
                                              Integer numberOfPayments) {
        BigDecimal monthlyInterestRate = annualRate.divide(BigDecimal.valueOf(1200), 10, RoundingMode.HALF_UP);
        BigDecimal powerResult = BigDecimal.ONE.add(monthlyInterestRate).pow(numberOfPayments);
        BigDecimal paymentNumerator = requestedAmount.multiply(monthlyInterestRate).multiply(powerResult);
        BigDecimal paymentDenominator = powerResult.subtract(BigDecimal.ONE);
        return paymentNumerator.divide(paymentDenominator, 2, RoundingMode.HALF_UP);
    }
}
