package io.project.statement.service;

import io.project.statement.model.dto.request.LoanStatementRequestDto;
import io.project.statement.model.dto.response.LoanOfferDto;

import java.util.List;

public interface StatementService {

    List<LoanOfferDto> sendStatement(LoanStatementRequestDto loanStatementRequestDto);

    void selectOffer(LoanOfferDto loanOfferDto);
}
