package io.project.deal.service.impl;

import io.project.deal.client.DealFeignClient;
import io.project.deal.data.DealTestData;
import io.project.deal.exception.LoanRequestDeniedException;
import io.project.deal.mapper.ClientMapper;
import io.project.deal.mapper.LoanOfferMapper;
import io.project.deal.mapper.PassportMapper;
import io.project.deal.mapper.ScoringDataMapper;
import io.project.deal.model.dto.response.CreditDto;
import io.project.deal.model.dto.response.LoanOfferDto;
import io.project.deal.model.entity.Credit;
import io.project.deal.service.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DealServiceImplTest {

    @Mock
    private ClientService clientService;

    @Mock
    private PassportService passportService;

    @Mock
    private StatementService statementService;

    @Mock
    private EmploymentService employmentService;

    @Mock
    private CreditService creditService;

    @Mock
    private ScoringDataMapper scoringDataMapper;

    @Mock
    private LoanOfferMapper loanOfferMapper;

    @Mock
    private PassportMapper passportMapper;

    @Mock
    private ClientMapper clientMapper;

    @Mock
    private DealFeignClient dealFeignClient;

    @InjectMocks
    private DealServiceImpl dealService;

    @Test
    void processLoanStatementRequest_returnsLoanOffers_whenRequestIsValid() {
        when(passportService.create(DealTestData.VALID_LOAN_STATEMENT_REQUEST_DTO))
                .thenReturn(DealTestData.SAVED_PASSPORT);
        when(clientService.create(DealTestData.VALID_LOAN_STATEMENT_REQUEST_DTO, DealTestData.SAVED_PASSPORT))
                .thenReturn(DealTestData.SAVED_CLIENT_ENTITY);
        when(statementService.create(DealTestData.SAVED_CLIENT_ENTITY)).thenReturn(DealTestData.SAVED_STATEMENT_ENTITY);
        when(dealFeignClient.sendLoanStatementRequest(DealTestData.VALID_LOAN_STATEMENT_REQUEST_DTO))
                .thenReturn(DealTestData.INITIAL_OFFERS);
        when(loanOfferMapper.setStatementIdToOfferList(DealTestData.INITIAL_OFFERS, DealTestData.STATEMENT_ID))
                .thenReturn(DealTestData.OFFERS_WITH_STATEMENT);

        List<LoanOfferDto> result = dealService
                .processLoanStatementRequest(DealTestData.VALID_LOAN_STATEMENT_REQUEST_DTO);

        assertNotNull(result);
        assertEquals(4, result.size());
        assertEquals(DealTestData.STATEMENT_ID, result.getFirst().getStatementId());

        verify(passportService).create(DealTestData.VALID_LOAN_STATEMENT_REQUEST_DTO);
        verify(clientService).create(DealTestData.VALID_LOAN_STATEMENT_REQUEST_DTO, DealTestData.SAVED_PASSPORT);
        verify(statementService).create(DealTestData.SAVED_CLIENT_ENTITY);
        verify(dealFeignClient).sendLoanStatementRequest(DealTestData.VALID_LOAN_STATEMENT_REQUEST_DTO);
        verify(loanOfferMapper).setStatementIdToOfferList(DealTestData.INITIAL_OFFERS, DealTestData.STATEMENT_ID);
    }

    @Test
    void processLoanStatementRequest_shouldHandleEmptyOffers() {
        List<LoanOfferDto> emptyOffers = List.of();

        when(passportService.create(DealTestData.VALID_LOAN_STATEMENT_REQUEST_DTO))
                .thenReturn(DealTestData.SAVED_PASSPORT);
        when(clientService.create(DealTestData.VALID_LOAN_STATEMENT_REQUEST_DTO, DealTestData.SAVED_PASSPORT))
                .thenReturn(DealTestData.SAVED_CLIENT_ENTITY);
        when(statementService.create(DealTestData.SAVED_CLIENT_ENTITY)).thenReturn(DealTestData.SAVED_STATEMENT_ENTITY);
        when(dealFeignClient.sendLoanStatementRequest(DealTestData.VALID_LOAN_STATEMENT_REQUEST_DTO))
                .thenReturn(emptyOffers);
        when(loanOfferMapper.setStatementIdToOfferList(emptyOffers, DealTestData.STATEMENT_ID)).thenReturn(emptyOffers);

        List<LoanOfferDto> result = dealService
                .processLoanStatementRequest(DealTestData.VALID_LOAN_STATEMENT_REQUEST_DTO);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void processCredit_shouldCreateCreditAndUpdateStatement_whenLoanApproved() {
        when(statementService.findById(DealTestData.STATEMENT_ID)).thenReturn(DealTestData.SAVED_STATEMENT_ENTITY);
        when(clientService.findById(DealTestData.CLIENT_ID)).thenReturn(DealTestData.SAVED_CLIENT_ENTITY);
        when(passportService.findById(DealTestData.PASSPORT_ID)).thenReturn(DealTestData.SAVED_PASSPORT);
        when(passportMapper.addFinishInformation(DealTestData.SAVED_PASSPORT,
                DealTestData.VALID_FINISH_REGISTRATION_REQUEST_DTO)).thenReturn(DealTestData.FINISHED_PASSPORT);
        when(employmentService.create(DealTestData.EMPLOYMENT_DTO_2)).thenReturn(DealTestData.SAVED_EMPLOYMENT_ENTITY_2);
        when(clientMapper.addFinishInformation(DealTestData.SAVED_CLIENT_ENTITY, DealTestData.SAVED_EMPLOYMENT_ENTITY_2,
                DealTestData.VALID_FINISH_REGISTRATION_REQUEST_DTO)).thenReturn(DealTestData.UPDATED_CLIENT_ENTITY);
        when(scoringDataMapper.toScoringDataDto(DealTestData.SAVED_STATEMENT_ENTITY,
                DealTestData.VALID_FINISH_REGISTRATION_REQUEST_DTO)).thenReturn(DealTestData.SCORING_DATA_DTO_1);

        when(dealFeignClient.sendScoringDataRequest(DealTestData.SCORING_DATA_DTO_1))
                .thenReturn(DealTestData.CREDIT_DTO_1);
        when(creditService.create(DealTestData.CREDIT_DTO_1)).thenReturn(DealTestData.SAVED_CREDIT_ENTITY_1);

        dealService.processCredit(DealTestData.VALID_FINISH_REGISTRATION_REQUEST_DTO,
                DealTestData.STATEMENT_ID.toString());

        verify(dealFeignClient).sendScoringDataRequest(DealTestData.SCORING_DATA_DTO_1);
        verify(creditService).create(DealTestData.CREDIT_DTO_1);
        verify(statementService)
                .mergeCreditIntoStatement(DealTestData.STATEMENT_ID.toString(), DealTestData.SAVED_CREDIT_ENTITY_1);
        verify(statementService, never()).declineClientAfterScoring(DealTestData.STATEMENT_ID.toString());
    }

    @Test
    void processCredit_shouldDeclineStatement_whenClientIsUnderscored() {
        when(statementService.findById(DealTestData.STATEMENT_ID)).thenReturn(DealTestData.SAVED_STATEMENT_ENTITY);
        when(clientService.findById(DealTestData.CLIENT_ID)).thenReturn(DealTestData.SAVED_CLIENT_ENTITY);
        when(passportService.findById(DealTestData.PASSPORT_ID)).thenReturn(DealTestData.SAVED_PASSPORT);
        when(passportMapper.addFinishInformation(DealTestData.SAVED_PASSPORT,
                DealTestData.UNEMPLOYED_FINISH_REGISTRATION_REQUEST_DTO)).thenReturn(DealTestData.FINISHED_PASSPORT);
        when(employmentService.create(DealTestData.UNEMPLOYED_EMPLOYMENT_DTO)).thenReturn(DealTestData.UNEMPLOYED_EMPLOYMENT_ENTITY);
        when(clientMapper.addFinishInformation(DealTestData.SAVED_CLIENT_ENTITY, DealTestData.UNEMPLOYED_EMPLOYMENT_ENTITY,
                DealTestData.UNEMPLOYED_FINISH_REGISTRATION_REQUEST_DTO)).thenReturn(DealTestData.UPDATED_CLIENT_ENTITY);
        when(scoringDataMapper.toScoringDataDto(DealTestData.SAVED_STATEMENT_ENTITY,
                DealTestData.UNEMPLOYED_FINISH_REGISTRATION_REQUEST_DTO)).thenReturn(DealTestData.INVALID_SCORING_DATA_DTO);

        when(dealFeignClient.sendScoringDataRequest(DealTestData.INVALID_SCORING_DATA_DTO))
                .thenThrow(new LoanRequestDeniedException("Loan request denied"));

        dealService.processCredit(DealTestData.UNEMPLOYED_FINISH_REGISTRATION_REQUEST_DTO, DealTestData.STATEMENT_ID.toString());

        verify(dealFeignClient).sendScoringDataRequest(DealTestData.INVALID_SCORING_DATA_DTO);
        verify(creditService, never()).create(any(CreditDto.class));
        verify(statementService, never()).mergeCreditIntoStatement(anyString(), any(Credit.class));
        verify(statementService).declineClientAfterScoring(DealTestData.STATEMENT_ID.toString());
    }
}