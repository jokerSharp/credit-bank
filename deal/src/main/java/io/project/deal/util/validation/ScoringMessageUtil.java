package io.project.deal.util.validation;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ScoringMessageUtil {

    public static final String CLIENT_HAS_NO_WORK_MESSAGE = "Клиент должен иметь работу";
    public static final String HIGH_REQUESTED_AMOUNT_MESSAGE = "Запрашиваемая сумма должна быть менее 24 зарплат клиента";
    public static final String CLIENT_UNDERAGE_MESSAGE = "Возраст клиента должен быть не менее 18 лет";
    public static final String INVALID_CLIENT_AGE_MESSAGE = "Возраст клиента должен быть от 20 до 65 лет";
    public static final String CLIENT_TOTAL_WORK_EXPERIENCE_MESSAGE = "Общий опыт работы клиента должен быть не менее 18 месяцев";
    public static final String CLIENT_CURRENT_WORK_EXPERIENCE_MESSAGE = "Опыт работы клиента на текущем месте должен быть не менее 3 месяцев";
}
