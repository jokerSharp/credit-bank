package io.project.dossier.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.project.dossier.model.entity.Credit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CreditDeserializer {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Credit deserialize(String json) {
        try {
            return objectMapper.readValue(json, Credit.class);
        } catch (JsonProcessingException e) {
            log.error("error occurred during deserializing json={}", e.getMessage());
        }
        return null;
    }
}
