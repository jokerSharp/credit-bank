package io.project.statement.controller.impl;

import io.project.statement.controller.StatementController;
import io.project.statement.model.dto.request.LoanStatementRequestDto;
import io.project.statement.model.dto.response.LoanOfferDto;
import io.project.statement.service.StatementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping(StatementControllerImpl.STATEMENT_ROOT_MAPPING)
@RestController
public class StatementControllerImpl implements StatementController {

    public static final String STATEMENT_ROOT_MAPPING = "/statement";
    public static final String SELECT_OFFER_MAPPING = "/offer";

    private final StatementService statementService;

    @PostMapping
    @Override
    public List<LoanOfferDto> statement(@RequestBody @Valid LoanStatementRequestDto loanStatementRequestDto) {
        return statementService.sendStatement(loanStatementRequestDto);
    }

    @PostMapping(SELECT_OFFER_MAPPING)
    @Override
    public void select(@RequestBody @Valid LoanOfferDto loanOfferDto) {
        statementService.selectOffer(loanOfferDto);
    }
}
