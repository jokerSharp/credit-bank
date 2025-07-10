package io.project.statement.service.impl;

import io.project.statement.client.StatementFeignClient;
import io.project.statement.exception.LoanRequestDeniedException;
import io.project.statement.model.dto.request.LoanStatementRequestDto;
import io.project.statement.model.dto.response.LoanOfferDto;
import io.project.statement.service.StatementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import static io.project.statement.validation.ScoringMessageUtil.CLIENT_UNDERAGE_MESSAGE;

@Slf4j
@RequiredArgsConstructor
@Service
public class StatementServiceImpl implements StatementService {

    private final StatementFeignClient statementFeignClient;

    @Override
    public List<LoanOfferDto> sendStatement(LoanStatementRequestDto loanStatementRequestDto) {
        int age = Period.between(loanStatementRequestDto.getBirthdate(), LocalDate.now()).getYears();
        if (age < 18) {
            log.error(CLIENT_UNDERAGE_MESSAGE);
            throw new LoanRequestDeniedException(CLIENT_UNDERAGE_MESSAGE);
        }
        log.info("receiving loan offer for request={}", loanStatementRequestDto);
        return statementFeignClient.sendLoanStatementRequest(loanStatementRequestDto);
    }

    @Override
    public void selectOffer(LoanOfferDto loanOfferDto) {
        log.info("selecting loan offer={}", loanOfferDto);
        statementFeignClient.select(loanOfferDto);
    }
}
