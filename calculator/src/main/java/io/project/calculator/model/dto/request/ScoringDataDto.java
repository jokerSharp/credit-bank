package io.project.calculator.model.dto.request;

import io.project.calculator.model.dto.enums.Gender;
import io.project.calculator.model.dto.enums.MaritalStatus;
import io.project.calculator.util.validation.group.NotBlankValidationGroup;
import io.project.calculator.util.validation.group.PatternValidationGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

import static io.project.calculator.util.validation.MessageForRequestUtil.*;

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
            "accountNumber": "12345678901234567890",
            "isInsuranceEnabled": true,
            "isSalaryClient": true
        }
        """)
@GroupSequence({NotBlankValidationGroup.class, PatternValidationGroup.class, ScoringDataDto.class})
@Builder
@Value
public class ScoringDataDto {
    @NotNull
    @Min(20000)
    BigDecimal amount;
    @NotNull
    @Min(6)
    Integer term;
    @NotBlank(message = FIRST_NAME_EMPTY, groups = NotBlankValidationGroup.class)
    @Size(min = 2, max = 30, message = FIRST_NAME_FORMAT, groups = PatternValidationGroup.class)
    String firstName;
    @NotBlank(message = LAST_NAME_EMPTY, groups = NotBlankValidationGroup.class)
    @Size(min = 2, max = 30, message = LAST_NAME_FORMAT, groups = PatternValidationGroup.class)
    String lastName;
    @Size(min = 2, max = 30, message = MIDDLE_NAME_FORMAT, groups = PatternValidationGroup.class)
    String middleName;
    @NotNull
    Gender gender;
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate birthdate;
    @NotBlank(message = PASSPORT_SERIES_EMPTY, groups = NotBlankValidationGroup.class)
    @Pattern(regexp = PASSPORT_SERIES_PATTERN, message = PASSPORT_SERIES_FORMAT, groups = PatternValidationGroup.class)
    String passportSeries;
    @NotBlank(message = PASSPORT_NUMBER_EMPTY, groups = NotBlankValidationGroup.class)
    @Pattern(regexp = PASSPORT_NUMBER_PATTERN, message = PASSPORT_NUMBER_FORMAT, groups = PatternValidationGroup.class)
    String passportNumber;
    @NotNull
    LocalDate passportIssueDate;
    @NotBlank(message = PASSPORT_ISSUE_BRANCH_EMPTY, groups = NotBlankValidationGroup.class)
    String passportIssueBranch;
    @NotNull
    MaritalStatus maritalStatus;
    @NotNull
    Integer dependentAmount;
    @NotNull
    EmploymentDto employment;
    @NotBlank(message = ACCOUNT_NUMBER_EMPTY, groups = NotBlankValidationGroup.class)
    @Pattern(regexp = ACCOUNT_NUMBER_PATTERN, message = ACCOUNT_NUMBER_FORMAT, groups = PatternValidationGroup.class)
    String accountNumber;
    @NotNull
    Boolean isInsuranceEnabled;
    @NotNull
    Boolean isSalaryClient;
}
