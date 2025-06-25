package io.project.deal.service.impl;

import io.project.deal.data.DealTestData;
import io.project.deal.mapper.CreditMapper;
import io.project.deal.model.entity.Credit;
import io.project.deal.repository.CreditRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

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

}