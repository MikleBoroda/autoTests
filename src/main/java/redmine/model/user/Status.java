package redmine.model.user;

import lombok.AllArgsConstructor;

import java.util.stream.Stream;

@AllArgsConstructor
public enum Status {

    UNREGISTERED(0, "Не зарегестрирован"),
    ACTIVE(1, "Активен"),
    UNACCEPTED(2, "Не подтвержден"),
    LOCKED(3, "Заблокирован");

    public final int statusCode;
    public final String description;

    public static Status getIntStatus(int code) {
        for (Status status : Status.values()) {
            if (status.statusCode == code) {
                return status;
            }
        }
        throw new IllegalStateException("Такого статуса нет" + code);
    }

    public static Status of(String description) {
        return Stream.of(values())
                .filter(status -> status.description.equals(description))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Не найдено Status по описанию " + description));
    }


}
