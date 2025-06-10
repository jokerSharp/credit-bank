package io.project.calculator.service;

import io.project.calculator.model.dto.request.LoanStatementRequestDto;
import io.project.calculator.model.dto.response.LoanOfferDto;

import java.util.List;

public interface OfferService {

    List<LoanOfferDto> offers(LoanStatementRequestDto loanStatementRequestDto);
}
