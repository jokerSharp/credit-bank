package io.project.calculator.dto;

import io.project.calculator.dto.enums.Gender;
import io.project.calculator.dto.enums.MaritalStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(example = """
        {
            "amount": 150000,
            "term": 10,
            "firstName": "John",
            "lastName": "Doe",
            "gender": "MALE",
            "birthdate": "2000-10-10",
            "passportSeries": "1234",
            "passportNumber": "567890",
            "passportIssueDate": "2020-10-10",
            "passportIssueBranch": "Federal bureau",
            "maritalStatus": "MARRIED",
            "dependentAmount": 1,
            "employment": {
                "employmentStatus": "BUSINESS_OWNER",
                "employerINN": "123456789012",
                "salary": 10000,
                "position": "TOP_MANAGER",
                "workExperienceTotal": 36,
                "workExperienceCurrent": 12
            },
            "accountNumber": "123456789012",
            "isInsuranceEnabled": true,
            "isSalaryClient": true
        }
        """)
public record ScoringDataDto(
        @NotNull
        @Min(20000)
        BigDecimal amount,
        @NotNull
        @Min(6)
        Integer term,
        @NotNull
        @Size(min = 2, max = 30)
        String firstName,
        @NotNull
        @Size(min = 2, max = 30)
        String lastName,
        @Size(min = 2, max = 30)
        String middleName,
        @NotNull
        Gender gender,
        @NotNull
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate birthdate,
        @NotNull
        @Pattern(regexp = "^\\d{4}$")
        String passportSeries,
        @NotNull
        @Pattern(regexp = "^\\d{6}$")
        String passportNumber,
        @NotNull
        LocalDate passportIssueDate,
        @NotNull
        String passportIssueBranch,
        @NotNull
        MaritalStatus maritalStatus,
        @NotNull
        Integer dependentAmount,
        @NotNull
        EmploymentDto employment,
        @NotNull
        String accountNumber,
        @NotNull
        Boolean isInsuranceEnabled,
        @NotNull
        Boolean isSalaryClient) {

    public ScoringDataDto(BigDecimal amount, Integer term, String firstName, String lastName, Gender gender,
                          LocalDate birthdate, String passportSeries, String passportNumber,
                          LocalDate passportIssueDate, String passportIssueBranch, MaritalStatus maritalStatus,
                          Integer dependentAmount, EmploymentDto employment, String accountNumber,
                          Boolean isInsuranceEnabled, Boolean isSalaryClient) {
        this(amount, term, firstName, lastName, null, gender, birthdate, passportSeries,
                passportNumber, passportIssueDate, passportIssueBranch, maritalStatus, dependentAmount,
                employment, accountNumber, isInsuranceEnabled, isSalaryClient);
    }
}
