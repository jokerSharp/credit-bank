package io.project.deal.model.dto.request;

import io.project.deal.util.validation.group.NotBlankValidationGroup;
import io.project.deal.util.validation.group.PatternValidationGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

import static io.project.deal.util.validation.MessageForRequestUtil.*;


@Schema(example = """
        {
            "amount": "123000",
            "term": "10",
            "firstName": "John",
            "lastName": "Doe",
            "email": "john@mail.com",
            "birthdate": "2000-10-10",
            "passportSeries": "1234",
            "passportNumber": "567890"
        }
        """)
@GroupSequence({NotBlankValidationGroup.class, PatternValidationGroup.class, LoanStatementRequestDto.class})
@Builder
@Value
public class LoanStatementRequestDto {
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
    @NotBlank(message = MAIL_EMPTY, groups = NotBlankValidationGroup.class)
    @Pattern(regexp = MAIL_PATTERN, message = MAIL_FORMAT, groups = PatternValidationGroup.class)
    String email;
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate birthdate;
    @NotBlank(message = PASSPORT_SERIES_EMPTY, groups = NotBlankValidationGroup.class)
    @Pattern(regexp = PASSPORT_SERIES_PATTERN, message = PASSPORT_SERIES_FORMAT, groups = PatternValidationGroup.class)
    String passportSeries;
    @NotBlank(message = PASSPORT_NUMBER_EMPTY, groups = NotBlankValidationGroup.class)
    @Pattern(regexp = PASSPORT_NUMBER_PATTERN, message = PASSPORT_NUMBER_FORMAT, groups = PatternValidationGroup.class)
    String passportNumber;
}
