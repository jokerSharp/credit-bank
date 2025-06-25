package io.project.deal.service;

import io.project.deal.model.dto.request.EmploymentDto;
import io.project.deal.model.entity.Employment;

public interface EmploymentService {
    Employment create(EmploymentDto employmentDto);
}
