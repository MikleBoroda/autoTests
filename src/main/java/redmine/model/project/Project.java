package redmine.model.project;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import redmine.model.Creatable;
import redmine.model.CreatableEntity;

import static redmine.model.StringUtils.randomHexString;
import static redmine.model.project.Status.*;

/**
 * Описание элементов модели "Проект"
 */
@NoArgsConstructor
@Getter
@Setter
public class Project extends CreatableEntity implements Creatable<Project> {

    private String name = "Kuznetsov" + randomHexString(10);
    private String description ="DescriptionProject" + randomHexString(10);
    private String homepage ="Mixa" + randomHexString(10);
    private Boolean isPublic = false;
    private Integer parentId;
    private String identifier = randomHexString(12);
    private Status status = OPENED;
    private Integer lft = 9;
    private Integer rgt = 10;
    private Boolean inheritMembers = false; //checkBox "Наследовать участников"
    private Integer defaultVersionId; // id из таблицы Version
    private Integer defaultAssignedToId;

    @Override
    public Project create() {
        // TODO:Реализовать с помощью запроса к БД
        throw new UnsupportedOperationException();

    }


}
