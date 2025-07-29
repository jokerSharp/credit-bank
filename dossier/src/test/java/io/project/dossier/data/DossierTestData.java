package io.project.dossier.data;

import io.project.dossier.model.dto.request.EmailMessageDto;
import io.project.dossier.model.entity.Credit;
import io.project.dossier.model.enums.CreditStatus;
import io.project.dossier.model.enums.EmailMessageTheme;
import io.project.dossier.util.EmailMessageUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class DossierTestData {

    public static final UUID STATEMENT_ID = UUID.randomUUID();
    public static final UUID CREDIT_ID = UUID.randomUUID();
    public static final String SES_CODE = "654321";
    public static final String EMAIL = "john.doe@mail.com";

    public static final Credit SAVED_CREDIT_ENTITY_1 = Credit.builder()
            .creditId(CREDIT_ID)
            .amount(BigDecimal.valueOf(100000))
            .term(10)
            .monthlyPayment(BigDecimal.valueOf(1208.00))
            .rate(BigDecimal.valueOf(7.9))
            .psk(BigDecimal.valueOf(144960.00))
            .isInsuranceEnabled(true)
            .isSalaryClient(false)
            .paymentSchedule(List.of())
            .creditStatus(CreditStatus.CALCULATED)
            .build();

    public static final EmailMessageDto FINISH_REGISTRATION_EMAIL_MESSAGE = EmailMessageDto.builder()
            .statementId(STATEMENT_ID)
            .theme(EmailMessageTheme.FINISH_REGISTRATION)
            .address(EMAIL)
            .text(EmailMessageUtils.finishRegistrationMailMessage("localhost:8082"))
            .build();

    public static final EmailMessageDto CREATE_DOCUMENTS_EMAIL_MESSAGE = EmailMessageDto.builder()
            .statementId(STATEMENT_ID)
            .theme(EmailMessageTheme.SEND_DOCUMENTS)
            .address(EMAIL)
            .text(EmailMessageUtils.createDocumentsMailMessage("localhost:8082"))
            .build();

    public static final EmailMessageDto STATEMENT_DENIED_EMAIL_MESSAGE = EmailMessageDto.builder()
            .statementId(STATEMENT_ID)
            .theme(EmailMessageTheme.STATEMENT_DENIED)
            .address(EMAIL)
            .text(EmailMessageUtils.statementDeniedMailMessage())
            .build();

    public static final String SERIALIZED_SAVED_CREDIT_ENTITY_1 = "{\"creditId\":\"031dcb19-5673-44cc-a7c8-03d6a164cf96\",\"amount\":100000,\"term\":10,\"monthlyPayment\":1208.0,\"rate\":7.9,\"psk\":144960.0,\"paymentSchedule\":[],\"isInsuranceEnabled\":true,\"isSalaryClient\":false,\"creditStatus\":\"CALCULATED\"}";

    public static final EmailMessageDto CREDIT_EMAIL_MESSAGE = EmailMessageDto.builder()
            .statementId(STATEMENT_ID)
            .theme(EmailMessageTheme.SEND_DOCUMENTS)
            .address(EMAIL)
            .text(SERIALIZED_SAVED_CREDIT_ENTITY_1)
            .build();

    public static final EmailMessageDto SES_CODE_EMAIL_MESSAGE = EmailMessageDto.builder()
            .statementId(STATEMENT_ID)
            .theme(EmailMessageTheme.SEND_SES)
            .address(EMAIL)
            .text(EmailMessageUtils.sesCodeMailMessage(SES_CODE))
            .build();

    public static final EmailMessageDto ONLY_SES_CODE_REQUEST_MESSAGE = EmailMessageDto.builder()
            .text(SES_CODE)
            .build();

    public static final EmailMessageDto CREDIT_ISSUED_EMAIL_MESSAGE = EmailMessageDto.builder()
            .statementId(STATEMENT_ID)
            .theme(EmailMessageTheme.CREDIT_ISSUED)
            .address(EMAIL)
            .text(EmailMessageUtils.creditIssuedMailMessage())
            .build();
}
