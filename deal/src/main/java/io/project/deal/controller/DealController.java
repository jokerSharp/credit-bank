package io.project.deal.controller;

import io.project.deal.model.dto.request.EmailMessageDto;
import io.project.deal.model.dto.request.FinishRegistrationRequestDto;
import io.project.deal.model.dto.request.LoanStatementRequestDto;
import io.project.deal.model.dto.response.LoanOfferDto;
import io.project.deal.model.entity.Statement;

import java.util.List;

public interface DealController {

    List<LoanOfferDto> statement(LoanStatementRequestDto loanStatementRequestDto);

    void selectOffer(LoanOfferDto loanOfferDto);

    void calculateCredit(FinishRegistrationRequestDto finishRegistrationRequestDto, String statementId);

    void sendDocuments(String statementId);

    void signDocuments(String statementId);

    void sendCode(EmailMessageDto emailMessageDto, String statementId);

    Statement getStatement(String statementId);

    List<Statement> getStatements();
}
