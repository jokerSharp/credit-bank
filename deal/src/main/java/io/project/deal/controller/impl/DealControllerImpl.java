package io.project.deal.controller.impl;

import io.project.deal.controller.DealController;
import io.project.deal.model.dto.request.EmailMessageDto;
import io.project.deal.model.dto.request.FinishRegistrationRequestDto;
import io.project.deal.model.dto.request.LoanStatementRequestDto;
import io.project.deal.model.dto.response.LoanOfferDto;
import io.project.deal.service.DealService;
import io.project.deal.service.StatementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping(DealControllerImpl.ROOT_DEAL_MAPPING)
@RestController
public class DealControllerImpl implements DealController {

    public static final String ROOT_DEAL_MAPPING = "/deal";
    public static final String STATEMENT_MAPPING = "/statement";
    public static final String OFFER_SELECT_MAPPING = "/offer/select";
    public static final String CALCULATE_MAPPING = "/calculate/";
    public static final String SEND_DOCUMENT_MAPPING = "/send";
    public static final String SIGN_DOCUMENT_MAPPING = "/sign";
    public static final String SES_CODE_MAPPING = "/code";

    private final StatementService statementService;

    private final DealService dealService;

    @PostMapping(STATEMENT_MAPPING)
    @Override
    public List<LoanOfferDto> statement(@RequestBody @Valid LoanStatementRequestDto loanStatementRequestDto) {
        return dealService.processLoanStatementRequest(loanStatementRequestDto);
    }

    @PostMapping(OFFER_SELECT_MAPPING)
    @Override
    public void selectOffer(@RequestBody @Valid LoanOfferDto loanOfferDto) {
        statementService.selectOffer(loanOfferDto);
    }

    @PostMapping(CALCULATE_MAPPING + "{statementId}")
    @Override
    public void calculateCredit(@RequestBody @Valid FinishRegistrationRequestDto finishRegistrationRequestDto,
                                @PathVariable String statementId) {
        dealService.processCredit(finishRegistrationRequestDto, statementId);
    }

    @PostMapping(CALCULATE_MAPPING + "{statementId}" + SEND_DOCUMENT_MAPPING)
    @Override
    public void sendDocuments(@PathVariable String statementId) {
        statementService.prepareDocuments(statementId);
    }

    @PostMapping(CALCULATE_MAPPING + "{statementId}" + SIGN_DOCUMENT_MAPPING)
    @Override
    public void signDocuments(@PathVariable String statementId) {
        statementService.signDocuments(statementId);
    }

    @PostMapping(CALCULATE_MAPPING + "{statementId}" + SES_CODE_MAPPING)
    @Override
    public void sendCode(@RequestBody EmailMessageDto emailMessageDto, @PathVariable String statementId) {
        statementService.verifySesCode(statementId, emailMessageDto);
    }
}
