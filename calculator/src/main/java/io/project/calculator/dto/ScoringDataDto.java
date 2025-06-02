package io.project.calculator.dto;

import io.project.calculator.dto.enums.Gender;
import io.project.calculator.dto.enums.MaritalStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

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
}
