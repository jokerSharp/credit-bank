package io.project.calculator.controller.impl;

import io.project.calculator.controller.CalculatorController;
import io.project.calculator.dto.CreditDto;
import io.project.calculator.dto.LoanOfferDto;
import io.project.calculator.dto.LoanStatementRequestDto;
import io.project.calculator.dto.ScoringDataDto;
import io.project.calculator.service.CalculatorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/calculator")
@RestController
public class CalculatorControllerImpl implements CalculatorController {

    private final CalculatorService calculatorService;

    @PostMapping("/offers")
    @Override
    public List<LoanOfferDto> offers(@RequestBody @Valid LoanStatementRequestDto loanStatementRequestDto) {
        log.info("received LoanStatementRequestDto request={}", loanStatementRequestDto);
        List<LoanOfferDto> offers = calculatorService.offers(loanStatementRequestDto);
        log.info("returning LoanOfferDtos={}", offers);
        return offers;
    }

    @PostMapping("/calc")
    @Override
    public CreditDto calculate(@RequestBody @Valid ScoringDataDto scoringDataDto) {
        log.info("received ScoringDataDto request={}", scoringDataDto);
        CreditDto creditDto = calculatorService.calculate(scoringDataDto);
        log.info("returning CreditDto={}", creditDto);
        return creditDto;
    }
}
