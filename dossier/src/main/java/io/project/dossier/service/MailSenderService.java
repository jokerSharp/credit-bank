package io.project.dossier.service;

import io.project.dossier.model.dto.request.EmailMessageDto;

public interface MailSenderService {

    void sendEmail(EmailMessageDto emailMessageDto);

    void sendEmailWithAttachment(EmailMessageDto emailMessageDto);
}
