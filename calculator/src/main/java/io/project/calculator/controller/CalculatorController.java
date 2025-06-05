package io.project.calculator.controller;

import io.project.calculator.model.dto.request.LoanStatementRequestDto;
import io.project.calculator.model.dto.request.ScoringDataDto;
import io.project.calculator.model.dto.response.CreditDto;
import io.project.calculator.model.dto.response.LoanOfferDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

public interface CalculatorController {

    @Operation(summary = "Get loan offers",
            description = "Returns four offer options after pre-scoring")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful calculation of loan offers"),
            @ApiResponse(responseCode = "400", description = "Unsuccessful calculation of loan offers")
    })
    List<LoanOfferDto> offers(LoanStatementRequestDto loanStatementRequestDto);

    @Operation(summary = "Get credit offer",
            description = "Returns single loan offer with payment schedule after scoring")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful calculation of loan offer"),
            @ApiResponse(responseCode = "400", description = "Unsuccessful calculation of loan offer")
    })
    CreditDto calculate(ScoringDataDto scoringDataDto);
}
