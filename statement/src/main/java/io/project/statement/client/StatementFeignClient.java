package io.project.statement.client;

import io.project.statement.config.FeignClientConfiguration;
import io.project.statement.model.dto.request.LoanStatementRequestDto;
import io.project.statement.model.dto.response.LoanOfferDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "statement-client",
        url = "http://localhost:8081",
        configuration = FeignClientConfiguration.class)
public interface StatementFeignClient {

    @PostMapping("/deal/statement")
    List<LoanOfferDto> sendLoanStatementRequest(@RequestBody LoanStatementRequestDto loanStatementRequestDto);

    @PostMapping("/deal/offer/select")
    void select(@RequestBody LoanOfferDto loanOfferDto);
}
