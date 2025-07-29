package io.project.deal.util.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.project.deal.model.dto.request.EmailMessageDto;
import org.apache.kafka.common.serialization.Serializer;

public class EmailMessageDtoSerializer implements Serializer<EmailMessageDto> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String s, EmailMessageDto emailMessageDto) {
        try {
            return objectMapper.writeValueAsBytes(emailMessageDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
