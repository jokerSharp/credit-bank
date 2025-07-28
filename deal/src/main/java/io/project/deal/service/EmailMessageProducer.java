package io.project.deal.service;

import io.project.deal.model.dto.request.EmailMessageDto;

public interface EmailMessageProducer {

    String FINISH_REGISTRATION_TOPIC = "finish-registration";
    String CREATE_DOCUMENTS_TOPIC = "create-documents";
    String SEND_DOCUMENTS_TOPIC = "send-documents";
    String SEND_SES_TOPIC = "send-ses";
    String CREDIT_ISSUED_TOPIC = "credit-issued";
    String STATEMENT_DENIED_TOPIC = "statement-denied";

    void sendMessage(String topic, EmailMessageDto emailMessageDto);
}
