package io.project.deal.client;

import io.project.deal.config.FeignClientConfiguration;
import io.project.deal.model.dto.request.LoanStatementRequestDto;
import io.project.deal.model.dto.request.ScoringDataDto;
import io.project.deal.model.dto.response.CreditDto;
import io.project.deal.model.dto.response.LoanOfferDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "loan-statement-client",
        url = "http://localhost:8080",
        configuration = FeignClientConfiguration.class)
public interface DealFeignClient {

    @PostMapping("/calculator/offers")
    List<LoanOfferDto> sendLoanStatementRequest(@RequestBody LoanStatementRequestDto loanStatementRequestDto);

    @PostMapping("/calculator/calc")
    CreditDto sendScoringDataRequest(@RequestBody ScoringDataDto scoringDataDto);
}
