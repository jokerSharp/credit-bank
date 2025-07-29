package io.project.deal.service.impl;

import io.project.deal.model.dto.request.EmailMessageDto;
import io.project.deal.service.EmailMessageProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaEmailMessageProducer implements EmailMessageProducer {

    private final KafkaTemplate<String, EmailMessageDto> kafkaTemplate;

    @Override
    public void sendMessage(String topic, EmailMessageDto emailMessageDto) {
        kafkaTemplate.send(topic, emailMessageDto)
                .whenComplete((result, e) -> {
                    if (e == null) {
                        log.info("Successfully sent message to the topic={}", result.getRecordMetadata().topic());
                    } else {
                        log.error("Failed to send message to the topic={} with exception={}", topic, e.getMessage());
                    }
                });
    }
}
