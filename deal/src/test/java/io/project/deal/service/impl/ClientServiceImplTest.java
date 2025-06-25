package io.project.deal.service.impl;

import io.project.deal.data.DealTestData;
import io.project.deal.mapper.ClientMapper;
import io.project.deal.model.dto.request.LoanStatementRequestDto;
import io.project.deal.model.entity.Client;
import io.project.deal.model.entity.Passport;
import io.project.deal.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static io.project.deal.util.validation.MessageForException.entityIdIsNullMessage;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private ClientServiceImpl clientService;

    @Test
    void shouldReturnClient_whenFoundById() {
        when(clientRepository.findById(DealTestData.CLIENT_ID))
                .thenReturn(Optional.of(DealTestData.SAVED_CLIENT_ENTITY));

        Client result = clientService.findById(DealTestData.CLIENT_ID);

        assertNotNull(result);
        assertEquals(DealTestData.SAVED_CLIENT_ENTITY, result);
        assertEquals(DealTestData.CLIENT_ID, result.getClientId());
        verify(clientRepository).findById(DealTestData.CLIENT_ID);
    }

    @Test
    void shouldThrowEntityNotFoundException_whenClientNotFound() {
        when(clientRepository.findById(DealTestData.CLIENT_ID)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> clientService.findById(DealTestData.CLIENT_ID));

        assertTrue(exception.getMessage().contains("Client"));
        assertTrue(exception.getMessage().contains(DealTestData.CLIENT_ID.toString()));
        verify(clientRepository).findById(DealTestData.CLIENT_ID);
    }

    @Test
    void shouldHandleNullIdParameter() {
        UUID nullId = null;
        when(clientRepository.findById(nullId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> clientService.findById(nullId));
        verify(clientRepository).findById(nullId);
    }

    @Test
    void shouldCreateClientSuccessfully_withValidInput() {
        when(clientMapper.ofLoanStatementRequestDto(DealTestData.VALID_LOAN_STATEMENT_REQUEST_DTO, DealTestData.SAVED_PASSPORT))
                .thenReturn(DealTestData.UNSAVED_CLIENT_ENTITY);
        when(clientRepository.save(DealTestData.UNSAVED_CLIENT_ENTITY)).thenReturn(DealTestData.SAVED_CLIENT_ENTITY);

        Client result = clientService.create(DealTestData.VALID_LOAN_STATEMENT_REQUEST_DTO, DealTestData.SAVED_PASSPORT);

        assertNotNull(result);
        assertEquals(DealTestData.SAVED_CLIENT_ENTITY, result);
        assertEquals(DealTestData.CLIENT_ID, result.getClientId());

        verify(clientMapper).ofLoanStatementRequestDto(DealTestData.VALID_LOAN_STATEMENT_REQUEST_DTO,
                DealTestData.SAVED_PASSPORT);
        verify(clientRepository).save(DealTestData.UNSAVED_CLIENT_ENTITY);
    }

    @Test
    void shouldUpdateClientSuccessfully_whenClientHasValidId() {
        when(clientRepository.save(DealTestData.SAVED_CLIENT_ENTITY)).thenReturn(DealTestData.UPDATED_CLIENT_ENTITY);

        Client result = clientService.update(DealTestData.SAVED_CLIENT_ENTITY);

        assertNotNull(result);
        assertEquals(DealTestData.UPDATED_CLIENT_ENTITY, result);
        assertEquals(DealTestData.CLIENT_ID, result.getClientId());
        verify(clientRepository).save(DealTestData.SAVED_CLIENT_ENTITY);
    }

    @Test
    void shouldThrowIllegalArgumentException_whenClientIdIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> clientService.update(DealTestData.UNSAVED_CLIENT_ENTITY));

        assertTrue(exception.getMessage().contains(entityIdIsNullMessage(Client.class)));

        verify(clientRepository, never()).save(any());
    }
}
