package io.project.dossier.service.impl;

import io.project.dossier.data.DossierTestData;
import io.project.dossier.service.MailSenderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MessageConsumerImplTest {

    @Mock
    private MailSenderService mailSenderService;

    @InjectMocks
    private MessageConsumerImpl messageConsumer;

    @Test
    void finishRegistration_shouldCallSendEmail() {
        messageConsumer.finishRegistration(DossierTestData.FINISH_REGISTRATION_EMAIL_MESSAGE);

        verify(mailSenderService, times(1))
                .sendEmail(DossierTestData.FINISH_REGISTRATION_EMAIL_MESSAGE);
    }

    @Test
    void createDocument_shouldCallSendEmail() {
        messageConsumer.createDocument(DossierTestData.CREATE_DOCUMENTS_EMAIL_MESSAGE);

        verify(mailSenderService, times(1))
                .sendEmail(DossierTestData.CREATE_DOCUMENTS_EMAIL_MESSAGE);
    }

    @Test
    void declineStatement_shouldCallSendEmail() {
        messageConsumer.declineStatement(DossierTestData.STATEMENT_DENIED_EMAIL_MESSAGE);

        verify(mailSenderService, times(1))
                .sendEmail(DossierTestData.STATEMENT_DENIED_EMAIL_MESSAGE);
    }

    @Test
    void prepareDocuments_shouldCallSendEmail() {
        messageConsumer.prepareDocuments(DossierTestData.CREDIT_EMAIL_MESSAGE);

        verify(mailSenderService, times(1))
                .sendEmailWithAttachment(DossierTestData.CREDIT_EMAIL_MESSAGE);
    }

    @Test
    void signDocuments_shouldCallSendEmail() {
        messageConsumer.signDocuments(DossierTestData.SES_CODE_EMAIL_MESSAGE);

        verify(mailSenderService, times(1))
                .sendEmail(DossierTestData.SES_CODE_EMAIL_MESSAGE);
    }

    @Test
    void issueCredit_shouldCallSendEmail() {
        messageConsumer.issueCredit(DossierTestData.CREDIT_ISSUED_EMAIL_MESSAGE);

        verify(mailSenderService, times(1))
                .sendEmail(DossierTestData.CREDIT_ISSUED_EMAIL_MESSAGE);
    }
}