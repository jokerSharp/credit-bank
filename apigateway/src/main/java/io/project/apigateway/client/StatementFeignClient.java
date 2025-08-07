package io.project.apigateway.client;

import io.project.apigateway.config.FeignClientConfiguration;
import io.project.apigateway.model.dto.request.LoanStatementRequestDto;
import io.project.apigateway.model.dto.response.LoanOfferDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "statement-client", configuration = FeignClientConfiguration.class)
public interface StatementFeignClient {

    @PostMapping("/statement")
    List<LoanOfferDto> sendLoanStatementRequest(@RequestBody LoanStatementRequestDto loanStatementRequestDto);

    @PostMapping("/statement/offer")
    void selectOffer(@RequestBody LoanOfferDto loanOfferDto);
}
