package io.project.deal.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.project.deal.model.dto.request.EmailMessageDto;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfiguration {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public ProducerFactory<String, EmailMessageDto> kafkaResourceFactory(ObjectMapper objectMapper) {
        Map<String, Object> configs = new HashMap<>();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        JsonSerializer<EmailMessageDto> serializer = new JsonSerializer<>(objectMapper);
        serializer.setAddTypeInfo(false);

        return new DefaultKafkaProducerFactory<>(configs, new StringSerializer(), serializer);
    }

    @Bean
    public KafkaTemplate<String, EmailMessageDto> kafkaTemplate(ProducerFactory<String, EmailMessageDto> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
}
