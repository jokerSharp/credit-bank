package io.project.deal.service;

import io.project.deal.model.dto.request.LoanStatementRequestDto;
import io.project.deal.model.entity.Client;
import io.project.deal.model.entity.Passport;

import java.util.UUID;

public interface ClientService {
    Client create(LoanStatementRequestDto LoanStatementRequestDto, Passport passport);

    Client findById(UUID clientId);

    Client update(Client client);
}
