package redmine.db.reaquests;

import redmine.db.connection.PostgresConnection;
import redmine.model.role.Permissions;
import redmine.model.role.Role;

import java.util.List;
import java.util.Map;


public class RoleRequests implements Create<Role> {
    @Override
    public void create(Role role) {
        String query = "INSERT INTO public.roles\n" +
                "(id,\"name\", \"position\", assignable, builtin, permissions, issues_visibility," +
                " users_visibility, time_entries_visibility, all_roles_managed, settings)\n" +
                "VALUES(DEFAULT,?,?,?,?,?,?,?,?,?,?) RETURNING id;\n";

        List<Map<String, Object>> result = PostgresConnection.INSTANCE.executeQuery(
                query,
                role.getName(),
                role.getPosition(),
                role.getAssignable(),
                role.getBuiltin().builtinCode,
                addPermissions(role.getPermissionsList()),
                role.getIssuesVisibility().name(),
                role.getUsersVisibility().name(),
                role.getTimeEntriesVisibility().name(),
                role.getAll_roles_managed(),
                role.getSettings()
        );
        Integer id = (Integer) result.get(0).get("id");
        role.setId(id);
    }

    private String addPermissions(List<Permissions> permissionsList) {
        StringBuilder sb = new StringBuilder();
        sb.append("---\n");
        for (Permissions permissions : permissionsList) {
            sb.append("---\n");
            sb.append("- :").append(permissions.toString().toLowerCase()).append("\n");

        }
        return sb.toString();
    }


}

