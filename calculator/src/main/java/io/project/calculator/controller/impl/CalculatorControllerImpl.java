package io.project.calculator.controller.impl;

import io.project.calculator.controller.CalculatorController;
import io.project.calculator.model.dto.request.LoanStatementRequestDto;
import io.project.calculator.model.dto.request.ScoringDataDto;
import io.project.calculator.model.dto.response.CreditDto;
import io.project.calculator.model.dto.response.LoanOfferDto;
import io.project.calculator.service.CalculatorService;
import io.project.calculator.service.OfferService;
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
@RequestMapping(CalculatorControllerImpl.ROOT_CALCULATOR_MAPPING)
@RestController
public class CalculatorControllerImpl implements CalculatorController {

    public static final String ROOT_CALCULATOR_MAPPING = "/calculator";
    public static final String OFFERS_MAPPING = "/offers";
    public static final String CALCULATOR_MAPPING = "/calc";

    private final OfferService offerService;
    private final CalculatorService calculatorService;

    @PostMapping(OFFERS_MAPPING)
    @Override
    public List<LoanOfferDto> offers(@RequestBody @Valid LoanStatementRequestDto loanStatementRequestDto) {
        log.info("received LoanStatementRequestDto request={}", loanStatementRequestDto);
        List<LoanOfferDto> offers = offerService.offers(loanStatementRequestDto);
        log.info("returning LoanOfferDtos={}", offers);
        return offers;
    }

    @PostMapping(CALCULATOR_MAPPING)
    @Override
    public CreditDto calculate(@RequestBody @Valid ScoringDataDto scoringDataDto) {
        log.info("received ScoringDataDto request={}", scoringDataDto);
        CreditDto creditDto = calculatorService.calculate(scoringDataDto);
        log.info("returning CreditDto={}", creditDto);
        return creditDto;
    }
}
