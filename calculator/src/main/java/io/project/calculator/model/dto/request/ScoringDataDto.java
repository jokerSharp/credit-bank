package io.project.calculator.model.dto.request;

import io.project.calculator.model.dto.enums.Gender;
import io.project.calculator.model.dto.enums.MaritalStatus;
import io.project.calculator.util.validation.ValidAge;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
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
            "accountNumber": "12345678901234567890",
            "isInsuranceEnabled": true,
            "isSalaryClient": true
        }
        """)
@Builder
@Value
public class ScoringDataDto {
    @NotNull
    @Min(20000)
    BigDecimal amount;
    @NotNull
    @Min(6)
    Integer term;
    @NotNull
    @Size(min = 2, max = 30)
    String firstName;
    @NotNull
    @Size(min = 2, max = 30)
    String lastName;
    @Size(min = 2, max = 30)
    String middleName;
    @NotNull
    Gender gender;
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @ValidAge
    LocalDate birthdate;
    @NotNull
    @Pattern(regexp = "^\\d{4}$")
    String passportSeries;
    @NotNull
    @Pattern(regexp = "^\\d{6}$")
    String passportNumber;
    @NotNull
    LocalDate passportIssueDate;
    @NotNull
    String passportIssueBranch;
    @NotNull
    MaritalStatus maritalStatus;
    @NotNull
    Integer dependentAmount;
    @NotNull
    EmploymentDto employment;
    @NotNull
    @Pattern(regexp = "^\\d{20}$")
    String accountNumber;
    @NotNull
    Boolean isInsuranceEnabled;
    @NotNull
    Boolean isSalaryClient;
}
