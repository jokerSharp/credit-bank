package io.project.dossier.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.project.dossier.model.dto.request.EmailMessageDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

@Slf4j
public class EmailMessageDtoDeserializer implements Deserializer<EmailMessageDto> {

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public EmailMessageDto deserialize(String s, byte[] bytes) {
        try {
            return objectMapper.readValue(bytes, EmailMessageDto.class);
        } catch (IOException e) {
            log.error("error occurred during deserializing EmailMessageDto={}", e.getMessage());
        }
        return null;
    }
}
