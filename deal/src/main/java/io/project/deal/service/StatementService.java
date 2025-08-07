package io.project.deal.service;

import io.project.deal.model.dto.request.EmailMessageDto;
import io.project.deal.model.dto.response.LoanOfferDto;
import io.project.deal.model.entity.Client;
import io.project.deal.model.entity.Credit;
import io.project.deal.model.entity.Statement;

import java.util.List;
import java.util.UUID;

public interface StatementService {

    Statement findById(UUID id);

    Statement create(Client client);

    Statement update(Statement statement);

    void selectOffer(LoanOfferDto loanOfferDto);

    void mergeCreditIntoStatement(String statementId, Credit credit);

    void declineClientAfterScoring(String statementId);

    void prepareDocuments(String statementId);

    void signDocuments(String statementId);

    void verifySesCode(String statementId, EmailMessageDto emailMessageDto);

    List<Statement> finalAll();
}
