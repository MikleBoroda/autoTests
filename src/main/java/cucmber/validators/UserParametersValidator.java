package cucmber.validators;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserParametersValidator {

    public static void validateUserParameters(Set<String> keys) {

        List<String> allowedKeys = Arrays.asList("Администратор", "Статус", "Уведомление о новых событиях","E-mail");

        boolean allKeysValid = keys.stream().allMatch(key -> allowedKeys.contains(key));
        if(!allKeysValid){
            throw new IllegalArgumentException("Среди переданных параметров пользователя есть некорректные параметры");
        }

    }
}
