package io.project.deal.service.impl;

import io.project.deal.data.DealTestData;
import io.project.deal.mapper.PassportMapper;
import io.project.deal.model.entity.Client;
import io.project.deal.model.entity.Passport;
import io.project.deal.repository.PassportRepository;
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
class PassportServiceImplTest {

    @Mock
    private PassportRepository passportRepository;

    @Mock
    private PassportMapper passportMapper;

    @InjectMocks
    private PassportServiceImpl passportService;

    @Test
    void shouldReturnPassport_whenFoundById() {
        when(passportRepository.findById(DealTestData.PASSPORT_ID))
                .thenReturn(Optional.of(DealTestData.SAVED_PASSPORT));

        Passport result = passportService.findById(DealTestData.PASSPORT_ID);

        assertNotNull(result);
        assertEquals(DealTestData.SAVED_PASSPORT, result);
        assertEquals(DealTestData.PASSPORT_ID, result.getPassportId());
        verify(passportRepository).findById(DealTestData.PASSPORT_ID);
    }

    @Test
    void shouldThrowEntityNotFoundException_whenPassportNotFound() {
        when(passportRepository.findById(DealTestData.PASSPORT_ID)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> passportService.findById(DealTestData.PASSPORT_ID));

        assertTrue(exception.getMessage().contains(entityNotFoundMessage(Passport.class, DealTestData.PASSPORT_ID)));
        verify(passportRepository).findById(DealTestData.PASSPORT_ID);
    }

    @Test
    void shouldCreatePassportSuccessfully_withValidStatement() {
        when(passportMapper.addInitialInformation(DealTestData.VALID_LOAN_STATEMENT_REQUEST_DTO))
                .thenReturn(DealTestData.UNSAVED_PASSPORT);
        when(passportRepository.save(DealTestData.UNSAVED_PASSPORT)).thenReturn(DealTestData.SAVED_PASSPORT);

        Passport result = passportService.create(DealTestData.VALID_LOAN_STATEMENT_REQUEST_DTO);

        assertNotNull(result);
        assertEquals(DealTestData.SAVED_PASSPORT, result);
        assertEquals(DealTestData.PASSPORT_ID, result.getPassportId());

        verify(passportMapper).addInitialInformation(DealTestData.VALID_LOAN_STATEMENT_REQUEST_DTO);
        verify(passportRepository).save(DealTestData.UNSAVED_PASSPORT);
    }

    @Test
    void shouldUpdatePassportSuccessfully_whenPassportHasValidId() {
        when(passportRepository.save(DealTestData.SAVED_PASSPORT)).thenReturn(DealTestData.FINISHED_PASSPORT);

        Passport result = passportService.update(DealTestData.SAVED_PASSPORT);

        assertNotNull(result);
        assertEquals(DealTestData.FINISHED_PASSPORT, result);
        assertEquals(DealTestData.PASSPORT_ID, result.getPassportId());
        verify(passportRepository).save(DealTestData.SAVED_PASSPORT);
    }

    @Test
    void shouldThrowIllegalArgumentException_whenPassportIdIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> passportService.update(DealTestData.UNSAVED_PASSPORT));

        assertTrue(exception.getMessage().contains(entityIdIsNullMessage(Passport.class)));

        verify(passportRepository, never()).save(any());
    }
}