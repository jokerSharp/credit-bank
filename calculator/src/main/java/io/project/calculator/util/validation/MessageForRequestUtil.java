package io.project.calculator.util.validation;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MessageForRequestUtil {

    public static final String FIRST_NAME_EMPTY = "Не указано имя клиента";
    public static final String FIRST_NAME_FORMAT = "Имя клиента должно содержать от 2 до 30 символов";

    public static final String LAST_NAME_EMPTY = "Не указана фамилия клиента";
    public static final String LAST_NAME_FORMAT = "Фамилия клиента должно содержать от 2 до 30 символов";

    public static final String MIDDLE_NAME_FORMAT = "Отчество клиента должно содержать от 2 до 30 символов";

    public static final String PASSPORT_SERIES_PATTERN = "^\\d{4}$";
    public static final String PASSPORT_SERIES_EMPTY = "Не указана серия паспорта клиента";
    public static final String PASSPORT_SERIES_FORMAT = "Серия паспорта клиента должна содержать 4 цифры";

    public static final String PASSPORT_NUMBER_PATTERN = "^\\d{6}$";
    public static final String PASSPORT_NUMBER_EMPTY = "Не указан номер паспорта клиента";
    public static final String PASSPORT_NUMBER_FORMAT = "Номер паспорта клиента должна содержать 6 цифр";

    public static final String PASSPORT_ISSUE_BRANCH_EMPTY = "Не указано подразделение, выдавшее паспорт клиента";

    public static final String MAIL_PATTERN = "^[a-z0-9A-Z_!#$%&'*+/=?`{|}~^.-]+@[a-z0-9A-Z.-]+$";
    public static final String MAIL_EMPTY = "Не указан email клиента";
    public static final String MAIL_FORMAT = "Некорректный формат электронной почты: %s";

    public static final String INN_PATTERN = "^\\d{12}$";
    public static final String INN_EMPTY = "Не указан инн клиента";
    public static final String INN_FORMAT = "Инн должен содержать 12 цифр";

    public static final String ACCOUNT_NUMBER_PATTERN = "^\\d{20}$";
    public static final String ACCOUNT_NUMBER_EMPTY = "Не указан номер счета клиента";
    public static final String ACCOUNT_NUMBER_FORMAT = "Номер счета должен содержать 20 цифр";
}
