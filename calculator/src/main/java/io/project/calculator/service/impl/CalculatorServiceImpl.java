package io.project.calculator.service.impl;

import io.project.calculator.model.dto.request.ScoringDataDto;
import io.project.calculator.model.dto.response.CreditDto;
import io.project.calculator.model.dto.response.PaymentScheduleElementDto;
import io.project.calculator.service.CalculatorService;
import io.project.calculator.service.OfferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class CalculatorServiceImpl implements CalculatorService {

    private final OfferService offerService;

    @Override
    public CreditDto calculate(ScoringDataDto scoringDataDto) {
        log.info("received ScoringDataDto object={}", scoringDataDto);
        BigDecimal adjustedRate = offerService.adjustRateToOption(scoringDataDto.getIsSalaryClient(),
                scoringDataDto.getIsInsuranceEnabled());
        BigDecimal actualRate = score(adjustedRate, scoringDataDto);
        log.debug("actualRate={}", actualRate);
        int numberOfPayments = scoringDataDto.getTerm() * 12;
        BigDecimal monthlyPayment = offerService.calculateMonthlyPayment(scoringDataDto.getAmount(), actualRate, numberOfPayments);
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

    private BigDecimal score(BigDecimal adjustedRate, ScoringDataDto scoringDataDto) {
        BigDecimal resultRate = adjustedRate;
        BigDecimal threePercent = BigDecimal.valueOf(3);

        resultRate = switch (scoringDataDto.getEmployment().getEmploymentStatus()) {
            case SELF_EMPLOYED -> resultRate.add(BigDecimal.TWO);
            case BUSINESS_OWNER -> resultRate.add(BigDecimal.ONE);
            default -> resultRate;
        };

        resultRate = switch (scoringDataDto.getEmployment().getPosition()) {
            case TOP_MANAGER -> resultRate.subtract(threePercent);
            case MIDDLE_MANAGER -> resultRate.subtract(BigDecimal.ONE);
            case LINEAR_EMPLOYEE -> resultRate;
        };

        resultRate = switch (scoringDataDto.getMaritalStatus()) {
            case MARRIED -> resultRate.subtract(threePercent);
            case DIVORCED -> resultRate.add(BigDecimal.ONE);
            case SINGLE -> resultRate;
        };

        int age = Period.between(scoringDataDto.getBirthdate(), LocalDate.now()).getYears();

        resultRate = switch (scoringDataDto.getGender()) {
            case MALE -> age >= 30 && age <= 55 ? resultRate.subtract(threePercent) : resultRate;
            case FEMALE -> age >= 32 && age <= 60 ? resultRate.subtract(threePercent) : resultRate;
            case NON_BINARY -> resultRate.add(BigDecimal.valueOf(7));
        };

        return resultRate;
    }
}
