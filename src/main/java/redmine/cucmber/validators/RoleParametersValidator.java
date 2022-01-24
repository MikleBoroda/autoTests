package redmine.cucmber.validators;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class RoleParametersValidator {
    public static void validateRoleParameters(Set<String> keys) {
        List<String> allowedKeys = Arrays.asList(
                "Просмотр задач"
        );

        boolean allKeysValid = keys.stream().allMatch(key -> allowedKeys.contains(key));
        if (!allKeysValid) {
            throw new IllegalArgumentException("Среди переданных параметров роли есть некорректные параметры");
        }

    }
}
