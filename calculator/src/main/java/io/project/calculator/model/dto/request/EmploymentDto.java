package io.project.calculator.model.dto.request;

import io.project.calculator.model.dto.enums.EmploymentStatus;
import io.project.calculator.model.dto.enums.Position;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Builder
@Value
public class EmploymentDto {
    @NotNull
    EmploymentStatus employmentStatus;
    @NotBlank
    @Pattern(regexp = "^\\d{12}$")
    String employerINN;
    @NotNull
    BigDecimal salary;
    @NotNull
    Position position;
    @NotNull
    Integer workExperienceTotal;
    @NotNull
    Integer workExperienceCurrent;

    public EmploymentDto(EmploymentStatus employmentStatus, String employerINN, BigDecimal salary, Position position, Integer workExperienceTotal, Integer workExperienceCurrent) {
        this.employmentStatus = employmentStatus;
        this.employerINN = employerINN;
        this.salary = salary;
        this.position = position;
        this.workExperienceTotal = workExperienceTotal;
        this.workExperienceCurrent = workExperienceCurrent;
    }
}
