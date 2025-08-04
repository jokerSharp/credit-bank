package io.project.apigateway.client;

import io.project.apigateway.config.FeignClientConfiguration;
import io.project.apigateway.model.dto.request.EmailMessageDto;
import io.project.apigateway.model.dto.request.FinishRegistrationRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "deal-client", configuration = FeignClientConfiguration.class)
public interface DealFeignClient {

    @PostMapping("/deal/calculate/{statementId}")
    void finishRegistration(@RequestBody FinishRegistrationRequestDto finishRegistrationRequestDto,
                            @PathVariable String statementId);

    @PostMapping("/deal/calculate/{statementId}/send")
    void createDocuments(@PathVariable String statementId);

    @PostMapping("/deal/calculate/{statementId}/sign")
    void signDocuments(@PathVariable String statementId);

    @PostMapping("/deal/calculate/{statementId}/code")
    void verifySesCode(@RequestBody EmailMessageDto emailMessageDto,
                       @PathVariable String statementId);
}
