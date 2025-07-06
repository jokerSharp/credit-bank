package io.project.statement.controller;

import io.project.statement.model.dto.request.LoanStatementRequestDto;
import io.project.statement.model.dto.response.LoanOfferDto;

import java.util.List;

public interface StatementController {
    List<LoanOfferDto> statement(LoanStatementRequestDto loanStatementRequestDto);

    void select(LoanOfferDto loanOfferDto);
}
