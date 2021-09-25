package redmine.model.role;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum IssuesVisibility {
    ALL("Все задачи"),
    DEFAULT("Только общие задачи"),
    OWN("Задачи созданные или назначенные пользователю");

    public final String description;

}
