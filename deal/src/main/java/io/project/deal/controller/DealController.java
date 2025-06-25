package io.project.deal.controller;

import io.project.deal.model.dto.request.FinishRegistrationRequestDto;
import io.project.deal.model.dto.request.LoanStatementRequestDto;
import io.project.deal.model.dto.response.LoanOfferDto;

import java.util.List;

public interface DealController {

    List<LoanOfferDto> statement(LoanStatementRequestDto loanStatementRequestDto);

    void selectOffer(LoanOfferDto loanOfferDto);

    void calculateCredit(FinishRegistrationRequestDto finishRegistrationRequestDto, String statementId);
}
