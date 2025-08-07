package io.project.apigateway.controller;

import io.project.apigateway.model.dto.request.EmailMessageDto;
import io.project.apigateway.model.dto.request.FinishRegistrationRequestDto;

public interface DealGatewayController {

    void finishRegistration(FinishRegistrationRequestDto finishRegistrationRequestDto, String statementId);

    void createDocuments(String statementId);

    void signDocuments(String statementId);

    void verifySesCode(EmailMessageDto emailMessageDto, String statementId);
}
