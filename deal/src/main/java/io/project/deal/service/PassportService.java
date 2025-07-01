package io.project.deal.service;

import io.project.deal.model.dto.request.LoanStatementRequestDto;
import io.project.deal.model.entity.Passport;

import java.util.UUID;

public interface PassportService {
    Passport create(LoanStatementRequestDto loanStatementRequestDto);

    Passport findById(UUID passportId);

    Passport update(Passport passport);
}
