package redmine.model.role;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import redmine.model.Creatable;
import redmine.model.Entity;

import java.util.Arrays;
import java.util.List;

import static redmine.model.StringUtils.*;

/**
 * Описание элементов модели "Роли"
 */
@NoArgsConstructor
@Getter
@Setter
public class Role extends Entity implements Creatable<Role> {

    private String name = "RoleInProject" + randomHexString(10);
    private Integer position = 35;
    private Boolean assignable = true;
    private Builtin builtin = Builtin.CURRENT_ROLE;
    private List<Permissions> permissionsList = Arrays.asList(Permissions.values());
    private IssuesVisibility issuesVisibility = IssuesVisibility.ALL;
    private UsersVisibility usersVisibility = UsersVisibility.ALL;
    private TimeEntriesVisibility timeEntriesVisibility = TimeEntriesVisibility.ALL;
    private Boolean all_roles_managed = true;
    private String settings;


    @Override
    public Role create() {
        // TODO:Реализовать с помощью запроса к БД
        throw new UnsupportedOperationException();
    }
}
