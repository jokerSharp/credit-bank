package io.project.apigateway.model.dto.request;

import io.project.apigateway.model.dto.enums.Gender;
import io.project.apigateway.model.dto.enums.MaritalStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

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
    BigDecimal amount;
    Integer term;
    String firstName;
    String lastName;
    String middleName;
    Gender gender;
    LocalDate birthdate;
    String passportSeries;
    String passportNumber;
    LocalDate passportIssueDate;
    String passportIssueBranch;
    MaritalStatus maritalStatus;
    Integer dependentAmount;
    EmploymentDto employment;
    String accountNumber;
    Boolean isInsuranceEnabled;
    Boolean isSalaryClient;
}
