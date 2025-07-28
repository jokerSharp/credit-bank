package io.project.dossier.service.impl;

import io.project.dossier.model.dto.request.EmailMessageDto;
import io.project.dossier.service.DocumentService;
import io.project.dossier.service.MailSenderService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MailSenderServiceImpl implements MailSenderService {

    private static final String ATTACHMENT_TITLE = "mortgage_agreement";

    @Value("${spring.mail.username}")
    private String emailFrom;

    @Value("${app.sign-documents.url}")
    private String signDocumentsUrl;

    private final JavaMailSender mailSender;

    private final DocumentService documentService;

    @Override
    public void sendEmail(EmailMessageDto emailMessageDto) {
        String emailTo = emailMessageDto.getAddress();
        String subject = emailMessageDto.getTheme().getThemeValue();
        String messageBody = emailMessageDto.getText();

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(emailFrom);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(messageBody);

        log.info("sending email to={}", emailTo);
        mailSender.send(mailMessage);
    }

    @Override
    public void sendEmailWithAttachment(EmailMessageDto emailMessageDto) {
        String emailTo = emailMessageDto.getAddress();
        String subject = emailMessageDto.getTheme().getThemeValue();
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);

            mmh.setTo(emailTo);
            mmh.setSubject(subject);
            mmh.setText(getMessageWithAttachmentBody(signDocumentsUrl));
            ByteArrayResource attachmentResource = documentService.generateCreditPdf(emailMessageDto.getText());
            mmh.addAttachment(ATTACHMENT_TITLE, attachmentResource, MediaType.APPLICATION_PDF_VALUE);

            mailSender.send(mimeMessage);
            log.info("email was sent to={}", emailTo);
        } catch (MessagingException e) {
            log.error("error occurred during the email with the attachment sending={}", e.getMessage());
        }
    }

    private String getMessageWithAttachmentBody(String url) {
        return """
                Уважаемый клиент!
                Проверьте и подпишите документы, запросив код по ссылке %s
                С уважением, Ваш Банк!""".formatted(url);
    }
}
