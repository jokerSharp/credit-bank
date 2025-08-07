package io.project.apigateway.model.dto.request;

import io.project.apigateway.model.dto.enums.Gender;
import io.project.apigateway.model.dto.enums.MaritalStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Schema(example = """
        {
            "gender": "MALE",
            "maritalStatus": "MARRIED",
            "dependentAmount": 1,
            "passportIssueDate": "2010-10-10",
            "passportIssueBranch": "Police station 47",
            "employment": {
                "employmentStatus": "EMPLOYED",
                "employerINN": "123456789012",
                "salary": 123000,
                "position": "TOP_MANAGER",
                "workExperienceTotal": 20,
                "workExperienceCurrent": 10
            },
            "accountNumber": "12345678901234567890"
        }
        """)
@Builder
@Value
public class FinishRegistrationRequestDto {
    Gender gender;
    MaritalStatus maritalStatus;
    Integer dependentAmount;
    LocalDate passportIssueDate;
    String passportIssueBranch;
    EmploymentDto employment;
    String accountNumber;
}
