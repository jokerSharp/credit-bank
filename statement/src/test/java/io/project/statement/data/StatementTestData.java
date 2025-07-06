package io.project.statement.data;

import io.project.statement.model.dto.request.LoanStatementRequestDto;
import io.project.statement.model.dto.response.LoanOfferDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class StatementTestData {

    public static final UUID STATEMENT_ID = UUID.randomUUID();
    public static final String PASSPORT_SERIES = "1234";
    public static final String PASSPORT_NUMBER = "567890";

    public static final LoanStatementRequestDto VALID_LOAN_STATEMENT_REQUEST_DTO = LoanStatementRequestDto.builder()
            .amount(BigDecimal.valueOf(100000))
            .term(10)
            .firstName("John")
            .lastName("Doe")
            .email("john.doe@mail.com")
            .birthdate(LocalDate.now().minusYears(30))
            .passportSeries(PASSPORT_SERIES)
            .passportNumber(PASSPORT_NUMBER)
            .build();

    public static final LoanStatementRequestDto UNDERAGE_LOAN_STATEMENT_REQUEST_DTO = LoanStatementRequestDto.builder()
            .amount(BigDecimal.valueOf(100000))
            .term(10)
            .firstName("John")
            .lastName("Doe")
            .email("john.doe@mail.com")
            .birthdate(LocalDate.now().minusYears(10))
            .passportSeries(PASSPORT_SERIES)
            .passportNumber(PASSPORT_NUMBER)
            .build();

    public static final LoanStatementRequestDto INVALID_LOAN_STATEMENT_REQUEST_DTO = LoanStatementRequestDto.builder()
            .amount(BigDecimal.valueOf(100))
            .term(5)
            .firstName("")
            .lastName("")
            .email("john.doe")
            .birthdate(LocalDate.now().minusYears(30))
            .passportSeries("")
            .passportNumber("")
            .build();

    public static final List<LoanOfferDto> OFFERS_WITH_STATEMENT = List.of(
            LoanOfferDto.builder().statementId(STATEMENT_ID).requestedAmount(BigDecimal.valueOf(100000))
                    .totalAmount(BigDecimal.valueOf(168714.00)).term(10).monthlyPayment(BigDecimal.valueOf(1405.95))
                    .rate(BigDecimal.valueOf(11.5)).isInsuranceEnabled(false).isSalaryClient(false).build(),
            LoanOfferDto.builder().statementId(STATEMENT_ID).requestedAmount(BigDecimal.valueOf(100000))
                    .totalAmount(BigDecimal.valueOf(165980.40)).term(10).monthlyPayment(BigDecimal.valueOf(1383.17))
                    .rate(BigDecimal.valueOf(11.1)).isInsuranceEnabled(false).isSalaryClient(true).build(),
            LoanOfferDto.builder().statementId(STATEMENT_ID).requestedAmount(BigDecimal.valueOf(100000))
                    .totalAmount(BigDecimal.valueOf(164622.00)).term(10).monthlyPayment(BigDecimal.valueOf(1371.85))
                    .rate(BigDecimal.valueOf(10.9)).isInsuranceEnabled(true).isSalaryClient(false).build(),
            LoanOfferDto.builder().statementId(STATEMENT_ID).requestedAmount(BigDecimal.valueOf(100000))
                    .totalAmount(BigDecimal.valueOf(161922.00)).term(10).monthlyPayment(BigDecimal.valueOf(1349.35))
                    .rate(BigDecimal.valueOf(10.5)).isInsuranceEnabled(true).isSalaryClient(true).build()
    );
}
