package io.project.deal.service;

import io.project.deal.model.dto.request.FinishRegistrationRequestDto;
import io.project.deal.model.dto.request.LoanStatementRequestDto;
import io.project.deal.model.dto.request.ScoringDataDto;
import io.project.deal.model.dto.response.LoanOfferDto;

import java.util.List;

public interface DealService {
    List<LoanOfferDto> processLoanStatementRequest(LoanStatementRequestDto loanStatementRequestDto);

    ScoringDataDto createScoringData(FinishRegistrationRequestDto finishRegistrationRequestDto,
                                     String statementId);

    void processCredit(FinishRegistrationRequestDto finishRegistrationRequestDto,
                       String statementId);
}
