package io.project.dossier.service;


import io.project.dossier.model.dto.request.EmailMessageDto;

public interface MessageConsumer {

    String FINISH_REGISTRATION_TOPIC = "finish-registration";
    String CREATE_DOCUMENTS_TOPIC = "create-documents";
    String SEND_DOCUMENTS_TOPIC = "send-documents";
    String SEND_SES_TOPIC = "send-ses";
    String CREDIT_ISSUED_TOPIC = "credit-issued";
    String STATEMENT_DENIED_TOPIC = "statement-denied";

    void finishRegistration(EmailMessageDto message, String topic, int partition, long offset);

    void createDocument(EmailMessageDto message, String topic, int partition, long offset);

    void declineStatement(EmailMessageDto message, String topic, int partition, long offset);

    void prepareDocuments(EmailMessageDto message, String topic, int partition, long offset);

    void signDocuments(EmailMessageDto message, String topic, int partition, long offset);

    void issueCredit(EmailMessageDto message, String topic, int partition, long offset);
}
