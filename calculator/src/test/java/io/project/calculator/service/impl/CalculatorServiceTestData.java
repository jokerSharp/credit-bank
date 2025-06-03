package io.project.calculator.service.impl;

import io.project.calculator.dto.EmploymentDto;
import io.project.calculator.dto.LoanStatementRequestDto;
import io.project.calculator.dto.ScoringDataDto;
import io.project.calculator.dto.enums.EmploymentStatus;
import io.project.calculator.dto.enums.Gender;
import io.project.calculator.dto.enums.MaritalStatus;
import io.project.calculator.dto.enums.Position;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CalculatorServiceTestData {

    public static LoanStatementRequestDto LOAN_STATEMENT_REQUEST_DTO = new LoanStatementRequestDto(
            BigDecimal.valueOf(100000),
            10,
            "John",
            "Dow",
            "john.dow@mail.com",
            LocalDate.now().minusYears(30),
            "1234",
            "5678");

    public static final EmploymentDto EMPLOYMENT_DTO_1 = new EmploymentDto(EmploymentStatus.EMPLOYED,
            "123456789012",
            BigDecimal.valueOf(5000),
            Position.MIDDLE_MANAGER,
            36,
            18);

    public static final EmploymentDto EMPLOYMENT_DTO_2 = new EmploymentDto(EmploymentStatus.BUSINESS_OWNER,
            "123456789012",
            BigDecimal.valueOf(7500),
            Position.TOP_MANAGER,
            24,
            6);

    public static final EmploymentDto EMPLOYMENT_DTO_3 = new EmploymentDto(EmploymentStatus.SELF_EMPLOYED,
            "123456789012",
            BigDecimal.valueOf(6050),
            Position.LINEAR_EMPLOYEE,
            50,
            50);

    public static ScoringDataDto SCORING_DATA_DTO_1 = new ScoringDataDto(BigDecimal.valueOf(100000),
            10,
            "John",
            "Dow",
            Gender.MALE,
            LocalDate.now().minusYears(40),
            "1234",
            "567890",
            LocalDate.now().minusYears(10),
            "Fleetwood st. 123",
            MaritalStatus.DIVORCED,
            1,
            EMPLOYMENT_DTO_1,
            "123456789012",
            true,
            false);

    public static ScoringDataDto SCORING_DATA_DTO_2 = new ScoringDataDto(BigDecimal.valueOf(200000),
            20,
            "Joan",
            "Dow",
            Gender.FEMALE,
            LocalDate.now().minusYears(35),
            "1234",
            "567890",
            LocalDate.now().minusYears(2),
            "Franzgasse 47",
            MaritalStatus.MARRIED,
            1,
            EMPLOYMENT_DTO_2,
            "123456789012",
            false,
            true);

    public static ScoringDataDto SCORING_DATA_DTO_3 = new ScoringDataDto(BigDecimal.valueOf(150000),
            12,
            "Magdalene",
            "Chante",
            Gender.NON_BINARY,
            LocalDate.now().minusYears(40),
            "1234",
            "567890",
            LocalDate.now().minusYears(5),
            "50 Jln Ghazali Jawi",
            MaritalStatus.SINGLE,
            1,
            EMPLOYMENT_DTO_3,
            "123456789012",
            true,
            true);
}
