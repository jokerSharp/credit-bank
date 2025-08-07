package io.project.deal.service.impl;

import io.project.deal.data.DealTestData;
import io.project.deal.exception.LoanRequestDeniedException;
import io.project.deal.mapper.EmailMessageMapper;
import io.project.deal.mapper.StatementMapper;
import io.project.deal.mapper.StatementStatusHistoryMapper;
import io.project.deal.model.entity.Statement;
import io.project.deal.repository.StatementRepository;
import io.project.deal.service.CreditService;
import io.project.deal.service.EmailMessageProducer;
import io.project.deal.util.EmailMessageUtils;
import io.project.deal.util.SesCodeUtils;
import io.project.deal.util.serialization.CreditSerializer;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static io.project.deal.util.validation.MessageForException.entityIdIsNullMessage;
import static io.project.deal.util.validation.MessageForException.entityNotFoundMessage;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StatementServiceImplTest {

    @Mock
    private CreditService creditService;

    @Mock
    private StatementRepository statementRepository;

    @Mock
    private StatementMapper statementMapper;

    @Mock
    private StatementStatusHistoryMapper statementStatusHistoryMapper;

    @Mock
    private EmailMessageMapper emailMessageMapper;

    @Mock
    private EmailMessageProducer emailMessageProducer;

    @Mock
    private CreditSerializer creditSerializer;

    @InjectMocks
    private StatementServiceImpl statementService;

    @Test
    void shouldReturnStatement_whenFoundById() {
        when(statementRepository.findById(DealTestData.STATEMENT_ID))
                .thenReturn(Optional.of(DealTestData.SAVED_STATEMENT_ENTITY));

        Statement result = statementService.findById(DealTestData.STATEMENT_ID);

        assertNotNull(result);
        assertEquals(DealTestData.SAVED_STATEMENT_ENTITY, result);
        assertEquals(DealTestData.STATEMENT_ID, result.getStatementId());
        verify(statementRepository).findById(DealTestData.STATEMENT_ID);
    }

    @Test
    void shouldReturnStatements_whenThereAreSavedStatements() {
        when(statementRepository.findAll())
                .thenReturn(List.of(DealTestData.SAVED_STATEMENT_ENTITY,
                        DealTestData.ANOTHER_SAVED_STATEMENT_ENTITY));

        List<Statement> result = statementService.finalAll();

        assertNotNull(result);
        assertEquals(DealTestData.STATEMENT_ID, result.getFirst().getStatementId());
        assertEquals(DealTestData.ANOTHER_STATEMENT_ID, result.getLast().getStatementId());
        verify(statementRepository).findAll();
    }

    @Test
    void shouldThrowEntityNotFoundException_whenStatementNotFound() {
        when(statementRepository.findById(DealTestData.STATEMENT_ID)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> statementService.findById(DealTestData.STATEMENT_ID));

        assertTrue(exception.getMessage().contains(entityNotFoundMessage(Statement.class, DealTestData.STATEMENT_ID)));
        verify(statementRepository).findById(DealTestData.STATEMENT_ID);
    }

    @Test
    void shouldSaveStatementSuccessfully_withValidClient() {
        when(statementMapper.addClient(DealTestData.SAVED_CLIENT_ENTITY))
                .thenReturn(DealTestData.UNSAVED_STATEMENT_ENTITY);
        when(statementRepository.save(DealTestData.UNSAVED_STATEMENT_ENTITY))
                .thenReturn(DealTestData.SAVED_STATEMENT_ENTITY);

        Statement result = statementService.create(DealTestData.SAVED_CLIENT_ENTITY);

        assertNotNull(result);
        assertEquals(DealTestData.SAVED_STATEMENT_ENTITY, result);
        assertEquals(DealTestData.STATEMENT_ID, result.getStatementId());

        verify(statementMapper).addClient(DealTestData.SAVED_CLIENT_ENTITY);
        verify(statementRepository).save(DealTestData.UNSAVED_STATEMENT_ENTITY);
    }

    @Test
    void shouldUpdateStatementSuccessfully_whenStatementHasId() {
        when(statementRepository.save(DealTestData.SAVED_STATEMENT_ENTITY))
                .thenReturn(DealTestData.APPROVED_STATEMENT_ENTITY);

        Statement result = statementService.update(DealTestData.SAVED_STATEMENT_ENTITY);

        assertNotNull(result);
        assertEquals(DealTestData.APPROVED_STATEMENT_ENTITY, result);
        assertEquals(DealTestData.STATEMENT_ID, result.getStatementId());
        verify(statementRepository).save(DealTestData.SAVED_STATEMENT_ENTITY);
    }

    @Test
    void shouldThrowIllegalArgumentException_whenStatementIdIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> statementService.update(DealTestData.UNSAVED_STATEMENT_ENTITY));

        assertTrue(exception.getMessage().contains(entityIdIsNullMessage(Statement.class)));

        verify(statementRepository, never()).save(any());
    }

    @Test
    void shouldSelectOffer_whenLoanOfferIsValid() {
        when(statementRepository.findById(DealTestData.STATEMENT_ID))
                .thenReturn(Optional.of(DealTestData.SAVED_STATEMENT_ENTITY));
        when(statementStatusHistoryMapper.toDto(DealTestData.SAVED_STATEMENT_ENTITY))
                .thenReturn(DealTestData.STATEMENT_STATUS_HISTORY_DTO);
        when(statementRepository.save(DealTestData.SAVED_STATEMENT_ENTITY))
                .thenReturn(DealTestData.APPROVED_STATEMENT_ENTITY);

        statementService.selectOffer(DealTestData.OFFERS_WITH_STATEMENT.getFirst());

        verify(statementRepository).findById(DealTestData.STATEMENT_ID);
        verify(statementStatusHistoryMapper).toDto(DealTestData.SAVED_STATEMENT_ENTITY);
        verify(statementRepository).save(DealTestData.SAVED_STATEMENT_ENTITY);
    }

    @Test
    void creditShouldMerged_whenCreditIsApproved() {
        when(statementRepository.findById(DealTestData.STATEMENT_ID))
                .thenReturn(Optional.of(DealTestData.APPROVED_STATEMENT_ENTITY));
        when(statementStatusHistoryMapper.toDto(DealTestData.APPROVED_STATEMENT_ENTITY))
                .thenReturn(DealTestData.STATEMENT_STATUS_HISTORY_DTO);
        when(statementRepository.save(DealTestData.APPROVED_STATEMENT_ENTITY))
                .thenReturn(DealTestData.WITH_CREDIT_STATEMENT_ENTITY);

        statementService.mergeCreditIntoStatement(DealTestData.STATEMENT_ID.toString(), DealTestData.SAVED_CREDIT_ENTITY_1);

        verify(statementRepository).findById(DealTestData.STATEMENT_ID);
        verify(statementStatusHistoryMapper).toDto(DealTestData.APPROVED_STATEMENT_ENTITY);
        verify(statementRepository).save(DealTestData.APPROVED_STATEMENT_ENTITY);
    }

    @Test
    void statementShouldBeDeclined_whenClientIsUnderscored() {
        when(statementRepository.findById(DealTestData.STATEMENT_ID))
                .thenReturn(Optional.of(DealTestData.APPROVED_STATEMENT_ENTITY));
        when(statementStatusHistoryMapper.toDto(DealTestData.APPROVED_STATEMENT_ENTITY))
                .thenReturn(DealTestData.STATEMENT_STATUS_HISTORY_DTO);
        when(statementRepository.save(DealTestData.APPROVED_STATEMENT_ENTITY))
                .thenReturn(DealTestData.DECLINED_STATEMENT_ENTITY);

        statementService.declineClientAfterScoring(DealTestData.STATEMENT_ID.toString());

        verify(statementRepository).findById(DealTestData.STATEMENT_ID);
        verify(statementStatusHistoryMapper).toDto(DealTestData.APPROVED_STATEMENT_ENTITY);
        verify(statementRepository).save(DealTestData.APPROVED_STATEMENT_ENTITY);
    }

    @Test
    void sendDocumentsMessageShouldBeSent_whenStatementIsCorrect() {
        when(statementRepository.findById(DealTestData.STATEMENT_ID))
                .thenReturn(Optional.of(DealTestData.WITH_CREDIT_STATEMENT_ENTITY));
        when(statementStatusHistoryMapper.toDto(DealTestData.WITH_CREDIT_STATEMENT_ENTITY))
                .thenReturn(DealTestData.STATEMENT_STATUS_HISTORY_DTO);
        when(creditSerializer.serialize(DealTestData.SAVED_CREDIT_ENTITY_1))
                .thenReturn(DealTestData.SERIALIZED_SAVED_CREDIT_ENTITY_1);
        when(emailMessageMapper.prepareDocuments(DealTestData.WITH_CREDIT_STATEMENT_ENTITY, DealTestData.SERIALIZED_SAVED_CREDIT_ENTITY_1))
                .thenReturn(DealTestData.CREDIT_EMAIL_MESSAGE);
        doNothing().when(emailMessageProducer)
                .sendMessage(EmailMessageProducer.SEND_DOCUMENTS_TOPIC, DealTestData.CREDIT_EMAIL_MESSAGE);

        statementService.prepareDocuments(DealTestData.STATEMENT_ID.toString());

        verify(statementRepository).findById(DealTestData.STATEMENT_ID);
        verify(emailMessageProducer).sendMessage(EmailMessageProducer.SEND_DOCUMENTS_TOPIC, DealTestData.CREDIT_EMAIL_MESSAGE);
    }

    @Test
    void verifySesCode_whenCodeIsCorrect() {
        when(statementRepository.findById(DealTestData.STATEMENT_ID))
                .thenReturn(Optional.of(DealTestData.WITH_CODE_STATEMENT_ENTITY));
        when(statementStatusHistoryMapper.toDto(DealTestData.WITH_CODE_STATEMENT_ENTITY))
                .thenReturn(DealTestData.STATEMENT_STATUS_HISTORY_DTO);
        when(emailMessageMapper.creditIssued(DealTestData.WITH_CODE_STATEMENT_ENTITY, EmailMessageUtils.creditIssuedMailMessage()))
                .thenReturn(DealTestData.CREDIT_ISSUED_EMAIL_MESSAGE);
        doNothing().when(emailMessageProducer)
                .sendMessage(EmailMessageProducer.CREDIT_ISSUED_TOPIC, DealTestData.CREDIT_ISSUED_EMAIL_MESSAGE);

        statementService.verifySesCode(DealTestData.STATEMENT_ID.toString(), DealTestData.ONLY_SES_CODE_REQUEST_MESSAGE);

        verify(statementRepository).findById(DealTestData.STATEMENT_ID);
        verify(statementStatusHistoryMapper, times(2)).toDto(DealTestData.WITH_CODE_STATEMENT_ENTITY);
        verify(creditService).update(DealTestData.WITH_CODE_STATEMENT_ENTITY.getCredit());
        verify(emailMessageProducer).sendMessage(EmailMessageProducer.CREDIT_ISSUED_TOPIC, DealTestData.CREDIT_ISSUED_EMAIL_MESSAGE);
    }

    @Test
    void statementIsUpdatedWithCode_whenCodeIsRequested() {
        ReflectionTestUtils.setField(statementService, "sendCodeUrl", DealTestData.STUB_URL);
        when(statementRepository.findById(DealTestData.STATEMENT_ID))
                .thenReturn(Optional.of(DealTestData.WITH_CREDIT_STATEMENT_ENTITY));
        when(statementRepository.save(DealTestData.WITH_CREDIT_STATEMENT_ENTITY))
                .thenReturn(DealTestData.WITH_CODE_STATEMENT_ENTITY);

        try (MockedStatic<SesCodeUtils> sesCodeUtilsMockedStatic = mockStatic(SesCodeUtils.class);
             MockedStatic<EmailMessageUtils> emailMessageUtilsMockedStatic = mockStatic(EmailMessageUtils.class)
        ) {
            sesCodeUtilsMockedStatic.when(SesCodeUtils::generateSesCode).thenReturn(DealTestData.SES_CODE);
            emailMessageUtilsMockedStatic.when(() -> EmailMessageUtils.sesCodeMailMessage(DealTestData.SES_CODE, DealTestData.STUB_URL))
                    .thenReturn(DealTestData.SES_MAIL_MESSAGE);

            when(emailMessageMapper.sendSesCode(DealTestData.WITH_CREDIT_STATEMENT_ENTITY, DealTestData.SES_MAIL_MESSAGE))
                    .thenReturn(DealTestData.SES_CODE_EMAIL_MESSAGE);
            doNothing().when(emailMessageProducer)
                    .sendMessage(EmailMessageProducer.SEND_SES_TOPIC, DealTestData.SES_CODE_EMAIL_MESSAGE);

            statementService.signDocuments(DealTestData.STATEMENT_ID.toString());

            verify(statementRepository).findById(DealTestData.STATEMENT_ID);
            verify(statementRepository).save(DealTestData.WITH_CREDIT_STATEMENT_ENTITY);
            verify(emailMessageProducer).sendMessage(EmailMessageProducer.SEND_SES_TOPIC, DealTestData.SES_CODE_EMAIL_MESSAGE);
        }
    }

    @Test
    void verifySesCodeShouldThrowException_whenCodeIsWrong() {
        when(statementRepository.findById(DealTestData.STATEMENT_ID))
                .thenReturn(Optional.of(DealTestData.WITH_CODE_STATEMENT_ENTITY));

        LoanRequestDeniedException exception = assertThrows(LoanRequestDeniedException.class,
                () -> statementService.verifySesCode(DealTestData.STATEMENT_ID.toString(), DealTestData.RANDOM_SES_CODE_EMAIL_MESSAGE));
        assertEquals(EmailMessageUtils.wrongCodeMailMessage(), exception.getMessage());

        verify(statementRepository).findById(DealTestData.STATEMENT_ID);
        verify(statementStatusHistoryMapper, never()).toDto(any());
        verify(creditService, never()).update(any());
        verify(emailMessageProducer, never()).sendMessage(any(), any());
    }

}