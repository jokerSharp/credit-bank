package io.project.deal.config;

import io.project.deal.service.EmailMessageProducer;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfiguration {

    @Bean
    public NewTopic finishRegistrationTopic() {
        return TopicBuilder.name(EmailMessageProducer.FINISH_REGISTRATION_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic createDocumentsTopic() {
        return TopicBuilder.name(EmailMessageProducer.CREATE_DOCUMENTS_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic sendDocumentsTopic() {
        return TopicBuilder.name(EmailMessageProducer.SEND_DOCUMENTS_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic sendSesTopic() {
        return TopicBuilder.name(EmailMessageProducer.SEND_SES_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic creditIssuedTopic() {
        return TopicBuilder.name(EmailMessageProducer.CREDIT_ISSUED_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic statementDeniedTopic() {
        return TopicBuilder.name(EmailMessageProducer.STATEMENT_DENIED_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }
}
