package io.project.deal.util.validation;

import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class MessageForException {

    public static <T> String entityNotFoundMessage(Class<T> aClass, UUID id) {
        return "%s with id %s is not found".formatted(aClass.getSimpleName(), id);
    }

    public static <T> String entityIdIsNullMessage(Class<T> aClass) {
        return "%s id cannot be null".formatted(aClass.getSimpleName());
    }
}
