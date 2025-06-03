package io.project.calculator.service.impl;

import io.project.calculator.dto.*;
import io.project.calculator.service.CalculatorService;
import io.project.calculator.service.LoanOfferOption;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CalculatorServiceImpl implements CalculatorService {

    @Value("${app.base-rate}")
    private BigDecimal baseRate;

    @Override
    public List<LoanOfferDto> offers(LoanStatementRequestDto loanStatementRequestDto) {
        int numberOfPayments = loanStatementRequestDto.term() * 12;
        List<LoanOfferDto> offers = new ArrayList<>();
        for (LoanOfferOption option : LoanOfferOption.values()) {
            BigDecimal adjustedRate = adjustRateToOption(option.isSalaryClient(), option.isInsuranceEnabled());
            BigDecimal monthlyPayment = calculateMonthlyPayment(loanStatementRequestDto.amount(), adjustedRate,
                    numberOfPayments);
            BigDecimal psk = monthlyPayment.multiply(BigDecimal.valueOf(numberOfPayments));
            offers.add(new LoanOfferDto(UUID.randomUUID(),
                    loanStatementRequestDto.amount(),
                    psk,
                    loanStatementRequestDto.term(),
                    monthlyPayment,
                    adjustedRate,
                    option.isInsuranceEnabled(),
                    option.isSalaryClient()));
        }
        return offers;
    }

    @Override
    public CreditDto calculate(ScoringDataDto scoringDataDto) {
        BigDecimal adjustedRate = adjustRateToOption(scoringDataDto.isSalaryClient(),
                scoringDataDto.isInsuranceEnabled());
        BigDecimal actualRate = score(adjustedRate, scoringDataDto);
        int numberOfPayments = scoringDataDto.term() * 12;
        BigDecimal monthlyPayment = calculateMonthlyPayment(scoringDataDto.amount(), actualRate, numberOfPayments);
        BigDecimal psk = monthlyPayment.multiply(BigDecimal.valueOf(numberOfPayments));
        List<PaymentScheduleElementDto> paymentSchedule = composePaymentSchedule(numberOfPayments, monthlyPayment,
                actualRate, psk);
        return new CreditDto(scoringDataDto.amount(),
                scoringDataDto.term(),
                monthlyPayment,
                actualRate,
                psk,
                scoringDataDto.isInsuranceEnabled(),
                scoringDataDto.isSalaryClient(),
                paymentSchedule);
    }

    private List<PaymentScheduleElementDto> composePaymentSchedule(int numberOfPayments,
                                                                   BigDecimal monthlyPayment,
                                                                   BigDecimal actualRate,
                                                                   BigDecimal psk) {
        List<PaymentScheduleElementDto> paymentSchedule = new ArrayList<>();
        for (int i = 1; i <= numberOfPayments; i++) {
            LocalDate paymentDate = LocalDate.now().plusMonths(i);
            BigDecimal remainingDebt = psk.subtract(monthlyPayment.multiply(BigDecimal.valueOf(i)));
            BigDecimal monthlyInterestRate = actualRate
                    .divide(BigDecimal.valueOf(1200), 10, RoundingMode.HALF_UP);
            BigDecimal interestPayment = remainingDebt.multiply(monthlyInterestRate)
                    .setScale(2, RoundingMode.HALF_UP);
            paymentSchedule.add(new PaymentScheduleElementDto(i,
                    paymentDate,
                    monthlyPayment,
                    interestPayment,
                    monthlyPayment.subtract(interestPayment),
                    remainingDebt));
        }
        return paymentSchedule;
    }

    private BigDecimal adjustRateToOption(boolean isSalaryClient, boolean isInsuranceEnabled) {
        BigDecimal resultRate = baseRate;
        if (isSalaryClient) {
            resultRate = resultRate.subtract(BigDecimal.valueOf(0.4));
        }
        if (isInsuranceEnabled) {
            resultRate = resultRate.subtract(BigDecimal.valueOf(0.6));
        }
        return resultRate;
    }

    /**
     * base loan formula is
     * payment = (principal * i * (1 + i) ^ numberOfPayments) / ((1 + i) ^ numberOfPayments - 1)
     * where:
     * payment - payment per month,
     * principal - initial loan amount
     * i - interest rate per month as a fraction (not percents)
     * numberOfPayments - number of actual payments, term of loan multiplied by 12
     *
     * @return payment per month
     */
    private BigDecimal calculateMonthlyPayment(BigDecimal requestedAmount,
                                               BigDecimal annualRate,
                                               Integer numberOfPayments) {
        BigDecimal monthlyInterestRate = annualRate.divide(BigDecimal.valueOf(1200), 10, RoundingMode.HALF_UP);
        BigDecimal powerResult = BigDecimal.ONE.add(monthlyInterestRate).pow(numberOfPayments);
        BigDecimal paymentNumerator = requestedAmount.multiply(monthlyInterestRate).multiply(powerResult);
        BigDecimal paymentDenominator = powerResult.subtract(BigDecimal.ONE);
        return paymentNumerator.divide(paymentDenominator, 2, RoundingMode.HALF_UP);
    }

    private BigDecimal score(BigDecimal adjustedRate, ScoringDataDto scoringDataDto) {
        BigDecimal resultRate = adjustedRate;
        BigDecimal threePercent = BigDecimal.valueOf(3);

        resultRate = switch (scoringDataDto.employment().employmentStatus()) {
            case SELF_EMPLOYED -> resultRate.add(BigDecimal.TWO);
            case BUSINESS_OWNER -> resultRate.add(BigDecimal.ONE);
            default -> resultRate;
        };

        resultRate = switch (scoringDataDto.employment().position()) {
            case TOP_MANAGER -> resultRate.subtract(threePercent);
            case MIDDLE_MANAGER -> resultRate.subtract(BigDecimal.ONE);
            case LINEAR_EMPLOYEE -> resultRate;
        };

        resultRate = switch (scoringDataDto.maritalStatus()) {
            case MARRIED -> resultRate.subtract(threePercent);
            case DIVORCED -> resultRate.add(BigDecimal.ONE);
            case SINGLE -> resultRate;
        };

        int age = Period.between(scoringDataDto.birthdate(), LocalDate.now()).getYears();

        resultRate = switch (scoringDataDto.gender()) {
            case MALE -> age >= 30 && age <= 55 ? resultRate.subtract(threePercent) : resultRate;
            case FEMALE -> age >= 32 && age <= 60 ? resultRate.subtract(threePercent) : resultRate;
            case NON_BINARY -> resultRate.add(BigDecimal.valueOf(7));
        };

        return resultRate;
    }
}
