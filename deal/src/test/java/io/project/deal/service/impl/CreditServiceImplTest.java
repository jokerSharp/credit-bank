package io.project.deal.service.impl;

import io.project.deal.data.DealTestData;
import io.project.deal.mapper.CreditMapper;
import io.project.deal.model.entity.Credit;
import io.project.deal.model.entity.Statement;
import io.project.deal.model.enums.CreditStatus;
import io.project.deal.repository.CreditRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static io.project.deal.util.validation.MessageForException.entityIdIsNullMessage;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreditServiceImplTest {

    @Mock
    private CreditRepository creditRepository;

    @Mock
    private CreditMapper creditMapper;

    @InjectMocks
    private CreditServiceImpl creditServiceImpl;

    @Test
    void saveCredit_returnsSavedCredit() {
        when(creditMapper.toEntity(DealTestData.CREDIT_DTO_1)).thenReturn(DealTestData.INITIAL_CREDIT_ENTITY_1);
        when(creditRepository.save(DealTestData.INITIAL_CREDIT_ENTITY_1)).thenReturn(DealTestData.SAVED_CREDIT_ENTITY_1);

        Credit result = creditServiceImpl.create(DealTestData.CREDIT_DTO_1);

        assertNotNull(result);
        assertEquals(DealTestData.SAVED_CREDIT_ENTITY_1, result);
        assertEquals(DealTestData.SAVED_CREDIT_ENTITY_1.getCreditId(), DealTestData.CREDIT_ID);
    }

    @Test
    void updateCredit_returnsSavedCredit() {
        when(creditRepository.save(DealTestData.SAVED_CREDIT_ENTITY_1)).thenReturn(DealTestData.ISSUED_CREDIT_ENTITY_1);

        Credit result = creditServiceImpl.update(DealTestData.SAVED_CREDIT_ENTITY_1);

        assertNotNull(result);
        assertEquals(DealTestData.SAVED_CREDIT_ENTITY_1.getCreditStatus(), CreditStatus.ISSUED);
    }

    @Test
    void shouldThrowIllegalArgumentException_whenStatementIdIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> creditServiceImpl.update(DealTestData.INITIAL_CREDIT_ENTITY_1));

        assertTrue(exception.getMessage().contains(entityIdIsNullMessage(Credit.class)));

        verify(creditRepository, never()).save(any());
    }
}