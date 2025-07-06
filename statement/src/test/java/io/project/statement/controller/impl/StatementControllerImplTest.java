package io.project.statement.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.project.statement.data.StatementTestData;
import io.project.statement.exception.LoanRequestDeniedException;
import io.project.statement.model.dto.response.LoanOfferDto;
import io.project.statement.service.StatementService;
import io.project.statement.service.impl.StatementServiceImpl;
import io.project.statement.validation.ScoringMessageUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static io.project.statement.controller.impl.StatementControllerImpl.SELECT_OFFER_MAPPING;
import static io.project.statement.controller.impl.StatementControllerImpl.STATEMENT_ROOT_MAPPING;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StatementControllerImpl.class)
@Import({StatementServiceImpl.class})
class StatementControllerImplTest {

    private static final String STATEMENT_REQUEST = STATEMENT_ROOT_MAPPING;
    private static final String OFFER_SELECT_REQUEST = STATEMENT_ROOT_MAPPING + SELECT_OFFER_MAPPING;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private StatementService statementService;

    @Test
    void statementServiceReturnsOffers_whenRequestIsValid() throws Exception {
        when(statementService.sendStatement(StatementTestData.VALID_LOAN_STATEMENT_REQUEST_DTO))
                .thenReturn(StatementTestData.OFFERS_WITH_STATEMENT);

        mockMvc.perform(MockMvcRequestBuilders.post(STATEMENT_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(StatementTestData.VALID_LOAN_STATEMENT_REQUEST_DTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    void statementServiceReturnsError_whenRequestIsInvalid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(STATEMENT_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(StatementTestData.INVALID_LOAN_STATEMENT_REQUEST_DTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void statementServiceReturnsError_whenClientIsUnderage() throws Exception {
        doThrow(new LoanRequestDeniedException(ScoringMessageUtil.CLIENT_UNDERAGE_MESSAGE))
                .when(statementService).sendStatement(StatementTestData.UNDERAGE_LOAN_STATEMENT_REQUEST_DTO);

        mockMvc.perform(MockMvcRequestBuilders.post(STATEMENT_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(StatementTestData.UNDERAGE_LOAN_STATEMENT_REQUEST_DTO)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void selectOffer_callsStatementService() throws Exception {
        doNothing().when(statementService).selectOffer(any(LoanOfferDto.class));

        mockMvc.perform(MockMvcRequestBuilders.post(OFFER_SELECT_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(StatementTestData.OFFERS_WITH_STATEMENT.getFirst())))
                .andExpect(status().isOk());
        verify(statementService, times(1)).selectOffer(any(LoanOfferDto.class));
    }

}