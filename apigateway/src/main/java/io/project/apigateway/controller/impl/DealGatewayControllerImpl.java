package io.project.apigateway.controller.impl;

import io.project.apigateway.client.DealFeignClient;
import io.project.apigateway.controller.DealGatewayController;
import io.project.apigateway.model.dto.request.EmailMessageDto;
import io.project.apigateway.model.dto.request.FinishRegistrationRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping
@RestController
public class DealGatewayControllerImpl implements DealGatewayController {

    private final DealFeignClient dealFeignClient;

    @PostMapping("statement/registration/{statementId}")
    @Override
    public void finishRegistration(@RequestBody FinishRegistrationRequestDto finishRegistrationRequestDto,
                                   @PathVariable String statementId) {
        log.info("finishing registration for statementId={}", statementId);
        dealFeignClient.finishRegistration(finishRegistrationRequestDto, statementId);
    }

    @PostMapping("document/{statementId}")
    @Override
    public void createDocuments(@PathVariable String statementId) {
        log.info("creating documents for statementId={}", statementId);
        dealFeignClient.createDocuments(statementId);
    }

    @PostMapping("document/{statementId}/sign")
    @Override
    public void signDocuments(@PathVariable String statementId) {
        log.info("requesting code for statementId={}", statementId);
        dealFeignClient.signDocuments(statementId);
    }

    @PostMapping("document/{statementId}/sign/code")
    @Override
    public void verifySesCode(@RequestBody EmailMessageDto emailMessageDto,
                              @PathVariable String statementId) {
        log.info("signing statementId={} with code", statementId);
        dealFeignClient.verifySesCode(emailMessageDto, statementId);
    }
}
