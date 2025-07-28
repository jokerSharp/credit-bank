package io.project.dossier.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EmailMessageUtils {

    public static String finishRegistrationMailMessage(String url) {
        String actualMessage = "Ваша заявка предварительно одобрена, завершите оформление, перейдя по ссылке %s".formatted(url);
        return getTemplateEmailMessage(actualMessage);
    }

    public static String createDocumentsMailMessage(String url) {
        String actualMessage = "Ваша заявка одобрена, сформируйте пакет документов, перейдя по ссылке %s".formatted(url);
        return getTemplateEmailMessage(actualMessage);
    }

    public static String sesCodeMailMessage(String code) {
        String actualMessage = "Код для подтверждения вашей заявки %s".formatted(code);
        return getTemplateEmailMessage(actualMessage);
    }

    public static String creditIssuedMailMessage() {
        String actualMessage = "Кредит по вашей заявке выпущен";
        return getTemplateEmailMessage(actualMessage);
    }

    public static String statementDeniedMailMessage() {
        String actualMessage = "Ваша заявка отклонена";
        return getTemplateEmailMessage(actualMessage);
    }

    private static String getTemplateEmailMessage(String actualMessage) {
        return """
                Уважаемый клиент!
                %s
                С уважением, Ваш Банк!""".formatted(actualMessage);
    }
}
