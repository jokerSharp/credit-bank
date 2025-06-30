package io.project.deal.model.dto.request;


import io.project.deal.model.enums.Gender;
import io.project.deal.model.enums.MaritalStatus;
import io.project.deal.util.validation.group.NotBlankValidationGroup;
import io.project.deal.util.validation.group.PatternValidationGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.GroupSequence;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

import static io.project.deal.util.validation.MessageForRequestUtil.*;

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
@GroupSequence({NotBlankValidationGroup.class, PatternValidationGroup.class, FinishRegistrationRequestDto.class})
@Builder
@Value
public class FinishRegistrationRequestDto {
    @NotNull
    Gender gender;
    @NotNull
    MaritalStatus maritalStatus;
    @NotNull
    Integer dependentAmount;
    @NotNull
    LocalDate passportIssueDate;
    @NotNull
    String passportIssueBranch;
    @Valid
    @NotNull
    EmploymentDto employment;
    @NotBlank(message = ACCOUNT_NUMBER_EMPTY, groups = NotBlankValidationGroup.class)
    @Pattern(regexp = ACCOUNT_NUMBER_PATTERN, message = ACCOUNT_NUMBER_FORMAT, groups = PatternValidationGroup.class)
    String accountNumber;
}
