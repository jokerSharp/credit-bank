package io.project.dossier.service.impl;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import io.project.dossier.model.entity.Credit;
import io.project.dossier.service.DocumentService;
import io.project.dossier.util.CreditDeserializer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Service
public class DocumentServiceImpl implements DocumentService {

    private final TemplateEngine templateEngine;

    private final CreditDeserializer creditDeserializer;

    @Override
    public ByteArrayResource generateCreditPdf(String creditJson) {
        Credit credit = creditDeserializer.deserialize(creditJson);
        log.info("parsed credit={}", credit);
        Context context = new Context();
        context.setVariable("credit", credit);
        String html = templateEngine.process("credit", context);

        byte[] result = null;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ConverterProperties properties = new ConverterProperties();
            HtmlConverter.convertToPdf(html, outputStream, properties);
            result =  outputStream.toByteArray();
        } catch (IOException e) {
            log.error("error occurred during the document conversion={}", e.getMessage());
        }
        return new ByteArrayResource(result);
    }
}
