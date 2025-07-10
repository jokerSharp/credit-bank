package io.project.statement.service.impl;

import io.project.statement.client.StatementFeignClient;
import io.project.statement.data.StatementTestData;
import io.project.statement.exception.LoanRequestDeniedException;
import io.project.statement.model.dto.response.LoanOfferDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StatementServiceImplTest {

    @Mock
    private StatementFeignClient statementFeignClient;

    @InjectMocks
    private StatementServiceImpl statementService;

    @Test
    void sendStatement_ShouldReturnCorrectNumberOfOffers() {
        when(statementService.sendStatement(StatementTestData.VALID_LOAN_STATEMENT_REQUEST_DTO))
                .thenReturn(StatementTestData.OFFERS_WITH_STATEMENT);

        List<LoanOfferDto> offers = statementService.sendStatement(StatementTestData.VALID_LOAN_STATEMENT_REQUEST_DTO);
        assertEquals(4, offers.size());
    }

    @Test
    void sendStatement_LoanRequestShouldBeDeclinedIfClientIsYounger18() {
        assertThrows(LoanRequestDeniedException.class,
                () -> statementService.sendStatement(StatementTestData.UNDERAGE_LOAN_STATEMENT_REQUEST_DTO));
    }

    @Test
    void shouldSelectOfferOffer_whenLoanOfferIsValid() {
        doNothing().when(statementFeignClient).select(any(LoanOfferDto.class));

        statementService.selectOffer(StatementTestData.OFFERS_WITH_STATEMENT.getFirst());

        verify(statementFeignClient, times(1)).select(any(LoanOfferDto.class));
    }

}