package io.project.apigateway.controller;

import io.project.apigateway.model.dto.request.LoanStatementRequestDto;
import io.project.apigateway.model.dto.response.LoanOfferDto;

import java.util.List;

public interface StatementGatewayController {

    List<LoanOfferDto> sendStatement(LoanStatementRequestDto loanStatementRequestDto);

    void selectOffer(LoanOfferDto loanOfferDto);
}
