package io.project.statement.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import io.project.statement.exception.LoanRequestDeniedException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class CustomFeignErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == 422) {
            try {
                String responseBody = Util.toString(response.body().asReader(StandardCharsets.UTF_8));
                JsonNode rootNode = objectMapper.readTree(responseBody);
                JsonNode errorsNode = rootNode.get("errors");
                if (errorsNode != null && !errorsNode.isEmpty()) {
                    String messageContent = errorsNode.get(0).get("message").asText();
                    return new LoanRequestDeniedException(messageContent);
                }
                return new LoanRequestDeniedException(responseBody);
            } catch (IOException e) {
                return new LoanRequestDeniedException("Failed to read response body");
            }
        }
        return defaultErrorDecoder.decode(methodKey, response);
    }
}
