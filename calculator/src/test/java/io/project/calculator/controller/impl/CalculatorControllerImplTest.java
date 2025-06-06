package io.project.calculator.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.project.calculator.controller.CalculatorController;
import io.project.calculator.data.CalculatorTestData;
import io.project.calculator.service.CalculatorService;
import io.project.calculator.service.OfferService;
import io.project.calculator.service.impl.CalculatorServiceImpl;
import io.project.calculator.service.impl.OfferServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static io.project.calculator.controller.impl.CalculatorControllerImpl.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CalculatorController.class)
@Import({OfferServiceImpl.class, CalculatorServiceImpl.class})
class CalculatorControllerImplTest {

    private static final String OFFER_REQUEST = ROOT_CALCULATOR_MAPPING + OFFERS_MAPPING;
    private static final String CALCULATOR_REQUEST = ROOT_CALCULATOR_MAPPING + CALCULATOR_MAPPING;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private OfferService offerService;

    @MockitoBean
    private CalculatorService calculatorService;

    @Test
    void offerServiceReturnsOffers_whenRequestIsValid() throws Exception {
        when(offerService.offers(CalculatorTestData.VALID_LOAN_STATEMENT_REQUEST_DTO)).thenReturn(CalculatorTestData.OFFERS);

        mockMvc.perform(MockMvcRequestBuilders.post(OFFER_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CalculatorTestData.VALID_LOAN_STATEMENT_REQUEST_DTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    void firstOfferIsBasic_whenRequestIsValid() throws Exception {
        when(offerService.offers(CalculatorTestData.VALID_LOAN_STATEMENT_REQUEST_DTO)).thenReturn(CalculatorTestData.OFFERS);

        mockMvc.perform(MockMvcRequestBuilders.post(OFFER_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CalculatorTestData.VALID_LOAN_STATEMENT_REQUEST_DTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].isInsuranceEnabled").value(false))
                .andExpect(jsonPath("$[0].isSalaryClient").value(false));
    }

    @Test
    void lastOfferWithInsuranceAndSalaryClient_whenRequestIsValid() throws Exception {
        when(offerService.offers(CalculatorTestData.VALID_LOAN_STATEMENT_REQUEST_DTO)).thenReturn(CalculatorTestData.OFFERS);

        mockMvc.perform(MockMvcRequestBuilders.post(OFFER_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CalculatorTestData.VALID_LOAN_STATEMENT_REQUEST_DTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[3].isInsuranceEnabled").value(true))
                .andExpect(jsonPath("$[3].isSalaryClient").value(true));
    }

    @Test
    void offerServiceReturnError_whenRequestIsInvalid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(OFFER_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CalculatorTestData.INVALID_LOAN_STATEMENT_REQUEST_DTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void calculatorServiceReturnsCreditOffer_whenRequestIsValid() throws Exception {
        when(calculatorService.calculate(CalculatorTestData.SCORING_DATA_DTO_1)).thenReturn(CalculatorTestData.CREDIT_DTO_1);

        mockMvc.perform(MockMvcRequestBuilders.post(CALCULATOR_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CalculatorTestData.SCORING_DATA_DTO_1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.psk").value("144960.0"));
    }

    @Test
    void calculatorServiceReturnError_whenRequestIsInvalid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(CALCULATOR_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CalculatorTestData.INVALID_SCORING_DATA_DTO)))
                .andExpect(status().isBadRequest());
    }
}