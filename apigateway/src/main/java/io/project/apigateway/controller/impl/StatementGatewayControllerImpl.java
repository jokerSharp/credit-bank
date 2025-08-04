package io.project.apigateway.controller.impl;

import io.project.apigateway.client.StatementFeignClient;
import io.project.apigateway.controller.StatementGatewayController;
import io.project.apigateway.model.dto.request.LoanStatementRequestDto;
import io.project.apigateway.model.dto.response.LoanOfferDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/statement")
@RestController
public class StatementGatewayControllerImpl implements StatementGatewayController {

    private final StatementFeignClient statementFeignClient;

    @PostMapping
    @Override
    public List<LoanOfferDto> sendStatement(@RequestBody LoanStatementRequestDto loanStatementRequestDto) {
        return statementFeignClient.sendLoanStatementRequest(loanStatementRequestDto);
    }

    @PostMapping("/select")
    @Override
    public void selectOffer(@RequestBody LoanOfferDto loanOfferDto) {
        statementFeignClient.selectOffer(loanOfferDto);
    }
}
