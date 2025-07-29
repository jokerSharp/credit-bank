package io.project.dossier.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmailMessageTheme {
    FINISH_REGISTRATION("Завершите оформление"),
    CREATE_DOCUMENTS("Сформируйте документы"),
    SEND_DOCUMENTS("Проверьте и подпишите документы"),
    SEND_SES("Код подтверждения"),
    CREDIT_ISSUED("Оформление кредита завершено"),
    STATEMENT_DENIED("Заявка отклонена");

    private final String themeValue;
}
