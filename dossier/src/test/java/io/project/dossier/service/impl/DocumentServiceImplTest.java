package io.project.dossier.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itextpdf.io.exceptions.IOException;
import io.project.dossier.data.DossierTestData;
import io.project.dossier.model.entity.Credit;
import io.project.dossier.util.CreditDeserializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ByteArrayResource;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DocumentServiceImplTest {

    @Mock
    private TemplateEngine templateEngine;

    @Mock
    private CreditDeserializer creditDeserializer;

    @InjectMocks
    private DocumentServiceImpl documentService;

    @Test
    void generateCreditPdfSuccess_whenJsonIsValid() {
        String creditJson = DossierTestData.SERIALIZED_SAVED_CREDIT_ENTITY_1;
        Credit expectedCredit = DossierTestData.SAVED_CREDIT_ENTITY_1;
        String templateTitle = "credit";
        String stubHtml = "<html><body><h1>Credit Document</h1></body></html>";

        when(creditDeserializer.deserialize(creditJson)).thenReturn(expectedCredit);
        when(templateEngine.process(eq(templateTitle), any(Context.class))).thenReturn(stubHtml);
        ByteArrayResource result = documentService.generateCreditPdf(creditJson);

        assertNotNull(result);
        assertTrue(result.contentLength() > 0);
        verify(creditDeserializer).deserialize(creditJson);
        verify(templateEngine).process(eq(templateTitle), any(Context.class));
    }
}