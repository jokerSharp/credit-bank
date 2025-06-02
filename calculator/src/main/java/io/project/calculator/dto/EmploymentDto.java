package io.project.calculator.dto;

import io.project.calculator.dto.enums.EmploymentStatus;
import io.project.calculator.dto.enums.Position;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public record EmploymentDto(
        @NotNull
        EmploymentStatus employmentStatus,
        @NotNull
        @Pattern(regexp = "^\\d{12}$")
        String employerINN,
        @NotNull
        BigDecimal salary,
        @NotNull
        Position position,
        @NotNull
        Integer workExperienceTotal,
        @NotNull
        Integer workExperienceCurrent) {
}
