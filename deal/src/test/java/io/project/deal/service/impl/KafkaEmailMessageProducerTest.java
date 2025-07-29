package io.project.deal.service.impl;

import io.project.deal.data.DealTestData;
import io.project.deal.model.dto.request.EmailMessageDto;
import io.project.deal.service.EmailMessageProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KafkaEmailMessageProducerTest {

    @Mock
    private KafkaTemplate<String, EmailMessageDto> kafkaTemplate;

    @Mock
    private ProducerRecord<String, EmailMessageDto> producerRecord;

    @Mock
    private RecordMetadata recordMetadata;

    private KafkaEmailMessageProducer producer;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        producer = new KafkaEmailMessageProducer(kafkaTemplate);
    }

    @Test
    void sendMessageIsSuccessful_whenKafkaIsUp() throws Exception {
        String topic = EmailMessageProducer.CREDIT_ISSUED_TOPIC;
        EmailMessageDto emailMessageDto = DealTestData.CREDIT_ISSUED_EMAIL_MESSAGE;
        ProducerRecord<String, EmailMessageDto> producerRecord = new ProducerRecord<>(topic, emailMessageDto);
        SendResult<String, EmailMessageDto> sendResult = new SendResult<>(producerRecord, recordMetadata);
        CompletableFuture<SendResult<String, EmailMessageDto>> future = CompletableFuture.completedFuture(sendResult);

        when(kafkaTemplate.send(topic, emailMessageDto)).thenReturn(future);

        producer.sendMessage(topic, emailMessageDto);

        verify(kafkaTemplate).send(topic, emailMessageDto);
    }

    @Test
    void sendMessageIsUnsuccessful_whenKafkaIsDown() throws Exception {
        String topic = EmailMessageProducer.CREDIT_ISSUED_TOPIC;
        EmailMessageDto emailMessageDto = DealTestData.CREDIT_ISSUED_EMAIL_MESSAGE;
        RuntimeException exception = new RuntimeException("Test exception");
        CompletableFuture<SendResult<String, EmailMessageDto>> future = CompletableFuture.failedFuture(exception);

        when(kafkaTemplate.send(topic, emailMessageDto)).thenReturn(future);

        producer.sendMessage(topic, emailMessageDto);

        verify(kafkaTemplate).send(topic, emailMessageDto);
    }
}