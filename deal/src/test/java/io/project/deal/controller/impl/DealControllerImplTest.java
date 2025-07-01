package io.project.deal.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.project.deal.data.DealTestData;
import io.project.deal.exception.LoanRequestDeniedException;
import io.project.deal.model.dto.response.LoanOfferDto;
import io.project.deal.model.entity.Statement;
import io.project.deal.service.DealService;
import io.project.deal.service.StatementService;
import io.project.deal.service.impl.DealServiceImpl;
import io.project.deal.service.impl.StatementServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static io.project.deal.controller.impl.DealControllerImpl.*;
import static io.project.deal.util.validation.MessageForException.entityNotFoundMessage;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DealControllerImpl.class)
@Import({StatementServiceImpl.class, DealServiceImpl.class})
class DealControllerImplTest {

    private static final String STATEMENT_REQUEST = ROOT_DEAL_MAPPING + STATEMENT_MAPPING;
    private static final String OFFER_SELECT_REQUEST = ROOT_DEAL_MAPPING + OFFER_SELECT_MAPPING;
    private static final String CALCULATE_CREDIT_REQUEST = ROOT_DEAL_MAPPING + CALCULATE_MAPPING + DealTestData.STATEMENT_ID;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private StatementService statementService;

    @MockitoBean
    private DealService dealService;

    @Test
    void offerServiceReturnsOffers_whenRequestIsValid() throws Exception {
        when(dealService.processLoanStatementRequest(DealTestData.VALID_LOAN_STATEMENT_REQUEST_DTO))
                .thenReturn(DealTestData.INITIAL_OFFERS);

        mockMvc.perform(MockMvcRequestBuilders.post(STATEMENT_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(DealTestData.VALID_LOAN_STATEMENT_REQUEST_DTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    void firstOfferIsBasic_whenRequestIsValid() throws Exception {
        when(dealService.processLoanStatementRequest(DealTestData.VALID_LOAN_STATEMENT_REQUEST_DTO))
                .thenReturn(DealTestData.INITIAL_OFFERS);

        mockMvc.perform(MockMvcRequestBuilders.post(STATEMENT_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(DealTestData.VALID_LOAN_STATEMENT_REQUEST_DTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].isInsuranceEnabled").value(false))
                .andExpect(jsonPath("$[0].isSalaryClient").value(false));
    }

    @Test
    void lastOfferWithInsuranceAndSalaryClient_whenRequestIsValid() throws Exception {
        when(dealService.processLoanStatementRequest(DealTestData.VALID_LOAN_STATEMENT_REQUEST_DTO))
                .thenReturn(DealTestData.INITIAL_OFFERS);

        mockMvc.perform(MockMvcRequestBuilders.post(STATEMENT_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(DealTestData.VALID_LOAN_STATEMENT_REQUEST_DTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[3].isInsuranceEnabled").value(true))
                .andExpect(jsonPath("$[3].isSalaryClient").value(true));

    }

    @Test
    void offerServiceReturnError_whenRequestIsInvalid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(STATEMENT_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(DealTestData.INVALID_LOAN_STATEMENT_REQUEST_DTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void selectOffer_callsStatementService() throws Exception {
        doNothing().when(statementService).selectOffer(any(LoanOfferDto.class));

        mockMvc.perform(MockMvcRequestBuilders.post(OFFER_SELECT_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(DealTestData.OFFERS_WITH_STATEMENT.getFirst())))
                .andExpect(status().isOk());
        verify(statementService, times(1)).selectOffer(any(LoanOfferDto.class));
    }

    @Test
    void calculateCredit_callsDealService() throws Exception {
        doNothing().when(dealService).processCredit(DealTestData.VALID_FINISH_REGISTRATION_REQUEST_DTO, DealTestData.STATEMENT_ID.toString());

        mockMvc.perform(MockMvcRequestBuilders.post(CALCULATE_CREDIT_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(DealTestData.VALID_FINISH_REGISTRATION_REQUEST_DTO)))
                .andExpect(status().isOk());
        verify(dealService, times(1))
                .processCredit(DealTestData.VALID_FINISH_REGISTRATION_REQUEST_DTO, DealTestData.STATEMENT_ID.toString());
    }

    @Test
    void statementDeclined_whenClientIsUnderscored() throws Exception {
        doThrow(new LoanRequestDeniedException("Loan request is denied"))
                .when(dealService).processCredit(DealTestData.UNEMPLOYED_FINISH_REGISTRATION_REQUEST_DTO, DealTestData.STATEMENT_ID.toString());

        mockMvc.perform(MockMvcRequestBuilders.post(CALCULATE_CREDIT_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(DealTestData.UNEMPLOYED_FINISH_REGISTRATION_REQUEST_DTO)))
                .andExpect(status().isUnprocessableEntity());
        verify(dealService, times(1))
                .processCredit(DealTestData.UNEMPLOYED_FINISH_REGISTRATION_REQUEST_DTO, DealTestData.STATEMENT_ID.toString());
    }

    @Test
    void statementIsNotProcessed_whenStatementNotFound() throws Exception {
        doThrow(new EntityNotFoundException(entityNotFoundMessage(Statement.class, DealTestData.STATEMENT_ID)))
                .when(dealService).processCredit(DealTestData.VALID_FINISH_REGISTRATION_REQUEST_DTO, DealTestData.STATEMENT_ID.toString());

        mockMvc.perform(MockMvcRequestBuilders.post(CALCULATE_CREDIT_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(DealTestData.VALID_FINISH_REGISTRATION_REQUEST_DTO)))
                .andExpect(status().isBadRequest());
        verify(dealService, times(1))
                .processCredit(DealTestData.VALID_FINISH_REGISTRATION_REQUEST_DTO, DealTestData.STATEMENT_ID.toString());
    }
}
