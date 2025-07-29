package io.project.dossier.service.impl;

import io.project.dossier.model.dto.request.EmailMessageDto;
import io.project.dossier.service.MailSenderService;
import io.project.dossier.service.MessageConsumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MessageConsumerImpl implements MessageConsumer {

    private final MailSenderService mailSenderService;

    @KafkaListener(topics = FINISH_REGISTRATION_TOPIC)
    @Override
    public void finishRegistration(@Payload EmailMessageDto message,
                                   @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                   @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                                   @Header(KafkaHeaders.OFFSET) long offset) {
        log.info("received finish registration message={}", message);
        mailSenderService.sendEmail(message);
    }

    @KafkaListener(topics = CREATE_DOCUMENTS_TOPIC)
    @Override
    public void createDocument(@Payload EmailMessageDto message,
                               @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                               @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                               @Header(KafkaHeaders.OFFSET) long offset) {
        log.info("received create documents message={}", message);
        mailSenderService.sendEmail(message);
    }

    @KafkaListener(topics = STATEMENT_DENIED_TOPIC)
    @Override
    public void declineStatement(@Payload EmailMessageDto message,
                                 @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                 @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                                 @Header(KafkaHeaders.OFFSET) long offset) {
        log.info("received statement denied message={}", message);
        mailSenderService.sendEmail(message);
    }

    @KafkaListener(topics = MessageConsumer.SEND_DOCUMENTS_TOPIC)
    @Override
    public void prepareDocuments(@Payload EmailMessageDto message,
                                 @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                 @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                                 @Header(KafkaHeaders.OFFSET) long offset) {
        log.info("received prepare documents message");
        mailSenderService.sendEmailWithAttachment(message);
    }

    @KafkaListener(topics = MessageConsumer.SEND_SES_TOPIC)
    @Override
    public void signDocuments(@Payload EmailMessageDto message,
                              @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                              @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                              @Header(KafkaHeaders.OFFSET) long offset) {
        log.info("received signed documents message");
        mailSenderService.sendEmail(message);
    }

    @KafkaListener(topics = MessageConsumer.CREDIT_ISSUED_TOPIC)
    @Override
    public void issueCredit(@Payload EmailMessageDto message,
                            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                            @Header(KafkaHeaders.OFFSET) long offset) {
        log.info("received send ses code message={}", message);
        mailSenderService.sendEmail(message);
    }
}
