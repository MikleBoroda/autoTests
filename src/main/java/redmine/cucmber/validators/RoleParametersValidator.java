package redmine.cucmber.validators;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class RoleParametersValidator {
    public static void validateRoleParameters(List<String> permissions) {
        List<String> allowedKeys = Arrays.asList(
                "Просмотр задач"
        );

        boolean allValuesValid = allowedKeys.containsAll(permissions);
        if (!allValuesValid) {
            throw new IllegalArgumentException("Среди переданных параметров роли есть некорректные параметры");
        }
    }
}
