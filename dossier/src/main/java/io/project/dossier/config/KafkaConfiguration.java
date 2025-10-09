package io.project.dossier.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.project.dossier.model.dto.request.EmailMessageDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfiguration {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    private String dealGroup;

    @Bean
    public ConsumerFactory<String, EmailMessageDto> consumerFactory(ObjectMapper objectMapper) {
        Map<String, Object> configs = new HashMap<>();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, dealGroup);

        JsonDeserializer<EmailMessageDto> deserializer = new JsonDeserializer<>(EmailMessageDto.class, objectMapper);

        return new DefaultKafkaConsumerFactory<>(configs, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, EmailMessageDto> kafkaListenerContainerFactory(
            ConsumerFactory<String, EmailMessageDto> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, EmailMessageDto> containerFactory = new ConcurrentKafkaListenerContainerFactory<>();
        containerFactory.setConcurrency(1);
        containerFactory.setConsumerFactory(consumerFactory);
        return containerFactory;
    }
}
