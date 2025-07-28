package io.project.deal.util;

import lombok.experimental.UtilityClass;

import java.security.SecureRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@UtilityClass
public class SesCodeUtils {

    private static final SecureRandom random = new SecureRandom();

    public static String generateSesCode() {
        return IntStream.range(0, 6)
                .mapToObj(i -> String.valueOf(random.nextInt(10)))
                .collect(Collectors.joining());
    }
}
