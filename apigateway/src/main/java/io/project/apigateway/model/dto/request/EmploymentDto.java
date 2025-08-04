package io.project.apigateway.model.dto.request;

import io.project.apigateway.model.dto.enums.EmploymentStatus;
import io.project.apigateway.model.dto.enums.Position;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Builder
@Value
public class EmploymentDto {
    EmploymentStatus employmentStatus;
    String employerINN;
    BigDecimal salary;
    Position position;
    Integer workExperienceTotal;
    Integer workExperienceCurrent;
}
