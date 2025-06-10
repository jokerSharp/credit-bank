package io.project.calculator.service.impl;

import io.project.calculator.model.dto.request.ScoringDataDto;
import io.project.calculator.model.dto.response.CreditDto;
import io.project.calculator.model.dto.response.PaymentScheduleElementDto;
import io.project.calculator.service.CalculatorService;
import io.project.calculator.service.ScoringService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class CalculatorServiceImpl implements CalculatorService {

    private static final Integer BASE_PERIODS_AMOUNT_PER_YEAR = 12;
    private static final Integer MONTHLY_INTEREST_RATE_DENOMINATOR = BASE_PERIODS_AMOUNT_PER_YEAR * 100;
    private static final Integer DEFAULT_BINARY_SCALE = 2;
    private static final Integer DEFAULT_DECIMAL_SCALE = 10;

    private final ScoringService scoringService;

    @Override
    public CreditDto calculate(ScoringDataDto scoringDataDto) {
        log.info("received ScoringDataDto object={}", scoringDataDto);
        BigDecimal actualRate = scoringService.score(scoringDataDto);
        int numberOfPayments = scoringDataDto.getTerm() * BASE_PERIODS_AMOUNT_PER_YEAR;
        BigDecimal monthlyPayment = calculateMonthlyPayment(scoringDataDto.getAmount(), actualRate, numberOfPayments);
        log.debug("monthlyPayment={}", monthlyPayment);
        BigDecimal psk = monthlyPayment.multiply(BigDecimal.valueOf(numberOfPayments));
        List<PaymentScheduleElementDto> paymentSchedule = composePaymentSchedule(numberOfPayments, monthlyPayment,
                actualRate, psk);
        CreditDto creditDto = CreditDto.builder()
                .amount(scoringDataDto.getAmount())
                .term(scoringDataDto.getTerm())
                .monthlyPayment(monthlyPayment)
                .rate(actualRate)
                .psk(psk)
                .isInsuranceEnabled(scoringDataDto.getIsInsuranceEnabled())
                .isSalaryClient(scoringDataDto.getIsSalaryClient())
                .paymentSchedule(paymentSchedule)
                .build();
        log.info("returning CreditDto={}", creditDto);
        return creditDto;
    }

    public BigDecimal calculateMonthlyPayment(BigDecimal requestedAmount,
                                              BigDecimal annualRate,
                                              Integer numberOfPayments) {
        BigDecimal monthlyInterestRate = calculateMonthlyInterestRate(annualRate);
        BigDecimal powerResult = BigDecimal.ONE.add(monthlyInterestRate).pow(numberOfPayments);
        BigDecimal paymentNumerator = requestedAmount.multiply(monthlyInterestRate).multiply(powerResult);
        BigDecimal paymentDenominator = powerResult.subtract(BigDecimal.ONE);
        return paymentNumerator.divide(paymentDenominator, DEFAULT_BINARY_SCALE, RoundingMode.HALF_UP);
    }

    private List<PaymentScheduleElementDto> composePaymentSchedule(int numberOfPayments,
                                                                   BigDecimal monthlyPayment,
                                                                   BigDecimal annualRate,
                                                                   BigDecimal psk) {
        List<PaymentScheduleElementDto> paymentSchedule = new ArrayList<>();
        for (int i = 1; i <= numberOfPayments; i++) {
            LocalDate paymentDate = LocalDate.now().plusMonths(i);
            BigDecimal remainingDebt = psk.subtract(monthlyPayment.multiply(BigDecimal.valueOf(i)));
            BigDecimal monthlyInterestRate = calculateMonthlyInterestRate(annualRate);
            BigDecimal interestPayment = remainingDebt.multiply(monthlyInterestRate)
                    .setScale(DEFAULT_BINARY_SCALE, RoundingMode.HALF_UP);
            paymentSchedule.add(PaymentScheduleElementDto.builder()
                    .number(i)
                    .date(paymentDate)
                    .totalPayment(monthlyPayment)
                    .interestPayment(interestPayment)
                    .debtPayment(monthlyPayment.subtract(interestPayment))
                    .remainingDebt(remainingDebt)
                    .build());
        }
        return paymentSchedule;
    }

    private BigDecimal calculateMonthlyInterestRate(BigDecimal rate) {
        return rate.divide(BigDecimal.valueOf(MONTHLY_INTEREST_RATE_DENOMINATOR),
                DEFAULT_DECIMAL_SCALE, RoundingMode.HALF_UP);
    }
}
