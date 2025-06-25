package io.project.deal.client;

import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import io.project.deal.exception.LoanRequestDeniedException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class CustomFeignErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == 422) {
            try {
                String responseBody = Util.toString(response.body().asReader(StandardCharsets.UTF_8));
                return new LoanRequestDeniedException(responseBody);
            } catch (IOException e) {
                return new LoanRequestDeniedException("Failed to read response body");
            }
        }
        return defaultErrorDecoder.decode(methodKey, response);
    }
}
