package io.project.dossier.service.impl;

import io.project.dossier.data.DossierTestData;
import io.project.dossier.model.dto.request.EmailMessageDto;
import io.project.dossier.service.DocumentService;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MailSenderServiceImplTest {

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private DocumentService documentService;

    @InjectMocks
    private MailSenderServiceImpl mailSenderService;

    private final String testEmailFrom = "from@example.com";

    @BeforeEach
    void setup() {
        ReflectionTestUtils.setField(mailSenderService, "emailFrom", testEmailFrom);
    }

    @Test
    void sendEmail_shouldSendSimpleMailMessage() {
        EmailMessageDto emailMessageDto = DossierTestData.CREDIT_ISSUED_EMAIL_MESSAGE;

        mailSenderService.sendEmail(emailMessageDto);

        ArgumentCaptor<SimpleMailMessage> mailCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender).send(mailCaptor.capture());

        SimpleMailMessage sentMessage = mailCaptor.getValue();
        assertEquals(testEmailFrom, sentMessage.getFrom());
        assertEquals(emailMessageDto.getAddress(), sentMessage.getTo()[0]);
        assertEquals(emailMessageDto.getTheme().getThemeValue(), sentMessage.getSubject());
        assertEquals(emailMessageDto.getText(), sentMessage.getText());
    }

    @Test
    void sendEmailWithAttachment_shouldSendMimeMessageWithAttachment() throws Exception {
        // Arrange
        EmailMessageDto emailMessageDto = DossierTestData.CREDIT_EMAIL_MESSAGE;
        ByteArrayResource attachmentResource = new ByteArrayResource(new byte[]{0, 1, 2});

        MimeMessage mimeMessageMock = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessageMock);

        spy(new MimeMessageHelper(mimeMessageMock, true));
        when(documentService.generateCreditPdf(emailMessageDto.getText())).thenReturn(attachmentResource);

        mailSenderService.sendEmailWithAttachment(emailMessageDto);

        verify(mailSender).createMimeMessage();
        verify(mailSender).send(mimeMessageMock);
        verify(documentService).generateCreditPdf(emailMessageDto.getText());
    }
}