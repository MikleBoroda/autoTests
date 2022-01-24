package redmine.cucmber.validators;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class ProjectParametersValidator {
    public static void validateProjectParameters(Set<String> keys) {
        List<String> allowedKeys = Arrays.asList(
                "Публичный"
        );

        boolean allKeysValid = keys.stream().allMatch(key -> allowedKeys.contains(key));
        if (!allKeysValid) {
            throw new IllegalArgumentException("Среди переданных параметров проекта есть некорректные параметры");
        }

    }

}
