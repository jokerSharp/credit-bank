package io.project.deal.service.impl;

import io.project.deal.data.DealTestData;
import io.project.deal.mapper.StatementMapper;
import io.project.deal.mapper.StatementStatusHistoryMapper;
import io.project.deal.model.entity.Statement;
import io.project.deal.repository.StatementRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static io.project.deal.util.validation.MessageForException.entityIdIsNullMessage;
import static io.project.deal.util.validation.MessageForException.entityNotFoundMessage;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StatementServiceImplTest {

    @Mock
    private StatementRepository statementRepository;

    @Mock
    private StatementMapper statementMapper;

    @Mock
    private StatementStatusHistoryMapper statementStatusHistoryMapper;

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
}