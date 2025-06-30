package io.project.deal.service.impl;

import io.project.deal.client.DealFeignClient;
import io.project.deal.exception.LoanRequestDeniedException;
import io.project.deal.mapper.*;
import io.project.deal.model.dto.request.FinishRegistrationRequestDto;
import io.project.deal.model.dto.request.LoanStatementRequestDto;
import io.project.deal.model.dto.request.ScoringDataDto;
import io.project.deal.model.dto.response.CreditDto;
import io.project.deal.model.dto.response.LoanOfferDto;
import io.project.deal.model.entity.*;
import io.project.deal.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class DealServiceImpl implements DealService {

    private final ClientService clientService;

    private final PassportService passportService;

    private final StatementService statementService;

    private final EmploymentService employmentService;

    private final CreditService creditService;

    private final ScoringDataMapper scoringDataMapper;

    private final LoanOfferMapper loanOfferMapper;

    private final PassportMapper passportMapper;

    private final ClientMapper clientMapper;

    private final DealFeignClient dealFeignClient;

    @Override
    public List<LoanOfferDto> processLoanStatementRequest(LoanStatementRequestDto loanStatementRequestDto) {
        log.info("processing loan statement request={}", loanStatementRequestDto);
        Passport passport = passportService.create(loanStatementRequestDto);
        Client client = clientService.create(loanStatementRequestDto, passport);
        Statement statement = statementService.create(client);
        List<LoanOfferDto> offers = dealFeignClient.sendLoanStatementRequest(loanStatementRequestDto);
        offers = loanOfferMapper.setStatementIdToOfferList(offers, statement.getStatementId());
        log.info("returning loan offers={}", offers);
        return offers;
    }

    @Override
    public ScoringDataDto createScoringData(FinishRegistrationRequestDto finishRegistrationRequestDto,
                                            String statementId) {
        log.info("creating scoring data for statement={} with finish registration request={}",
                statementId, finishRegistrationRequestDto);
        Statement statement = statementService.findById(UUID.fromString(statementId));

        finishClientRelatedEntitiesRegistration(finishRegistrationRequestDto, statement);

        ScoringDataDto scoringData = scoringDataMapper.toScoringDataDto(statement, finishRegistrationRequestDto);
        log.info("returning scoring data={}", scoringData);
        return scoringData;
    }

    private void finishClientRelatedEntitiesRegistration(FinishRegistrationRequestDto finishRegistrationRequestDto,
                                                         Statement statement) {
        Client client = clientService.findById(statement.getClient().getClientId());

        Passport passport = passportService.findById(client.getPassport().getPassportId());
        passport = passportMapper.addFinishInformation(passport, finishRegistrationRequestDto);
        passportService.update(passport);

        Employment employment = employmentService.create(finishRegistrationRequestDto.getEmployment());
        client = clientMapper.addFinishInformation(client, employment, finishRegistrationRequestDto);
        clientService.update(client);
    }

    @Override
    public void processCredit(FinishRegistrationRequestDto finishRegistrationRequestDto,
                              String statementId) {
        log.info("processing credit for statement={}", statementId);
        ScoringDataDto scoringData = createScoringData(finishRegistrationRequestDto, statementId);
        try {
            CreditDto creditDto = dealFeignClient.sendScoringDataRequest(scoringData);
            Credit credit = creditService.create(creditDto);
            log.info("update statement={} with credit={}", statementId, credit);
            statementService.mergeCreditIntoStatement(statementId, credit);
        } catch (LoanRequestDeniedException e) {
            log.info("loan request was denied for statement={}", statementId);
            statementService.declineClientAfterScoring(statementId);
            throw new LoanRequestDeniedException(e.getMessage());
        }
    }
}
