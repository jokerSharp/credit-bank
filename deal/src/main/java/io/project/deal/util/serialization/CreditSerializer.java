package io.project.deal.util.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.project.deal.model.entity.Credit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CreditSerializer {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String serialize(Credit credit) {
        try {
            return objectMapper.writeValueAsString(credit);
        } catch (JsonProcessingException e) {
            log.error("error occurred during credit serialization={}", e.getMessage());
        }
        return null;
    }
}
