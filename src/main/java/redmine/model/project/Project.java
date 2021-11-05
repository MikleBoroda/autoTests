package redmine.model.project;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import redmine.db.reaquests.ProjectRequests;
import redmine.model.Creatable;
import redmine.model.CreatableEntity;
import redmine.model.role.Role;
import redmine.model.user.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private String description = "DescriptionProject" + randomHexString(10);
    private String homepage = "Kuznetsov" + randomHexString(10);
    private Boolean isPublic = false;
    private Integer parentId;
    private String identifier = randomHexString(12);
    private Status status = OPENED;
    private Integer lft = 9;
    private Integer rgt = 10;
    private Boolean inheritMembers = false; //checkBox "Наследовать участников"
    private Integer defaultVersionId; // id из таблицы Version
    private Integer defaultAssignedToId;
    private Map<User, List<Role>> userMap = new HashMap<>();

    public void addUserAndRoles(User user, List<Role> roles) {
        userMap.put(user, roles);
    }


    @Override
    public Project create() {
        new ProjectRequests().create(this);
        return this;

    }


}
