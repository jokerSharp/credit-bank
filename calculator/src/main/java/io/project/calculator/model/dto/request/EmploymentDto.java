package io.project.calculator.model.dto.request;

import io.project.calculator.model.dto.enums.EmploymentStatus;
import io.project.calculator.model.dto.enums.Position;
import io.project.calculator.util.validation.group.NotBlankValidationGroup;
import io.project.calculator.util.validation.group.PatternValidationGroup;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

import static io.project.calculator.util.validation.MessageForRequestUtil.*;

@GroupSequence({NotBlankValidationGroup.class, PatternValidationGroup.class, EmploymentDto.class})
@Builder
@Value
public class EmploymentDto {
    @NotNull
    EmploymentStatus employmentStatus;
    @NotBlank(message = INN_EMPTY, groups = NotBlankValidationGroup.class)
    @Pattern(regexp = INN_PATTERN, message = INN_FORMAT, groups = PatternValidationGroup.class)
    String employerINN;
    @NotNull
    BigDecimal salary;
    @NotNull
    Position position;
    @NotNull
    Integer workExperienceTotal;
    @NotNull
    Integer workExperienceCurrent;
}
