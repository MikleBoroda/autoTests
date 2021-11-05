package redmine.db.reaquests;

import redmine.db.connection.PostgresConnection;
import redmine.model.CreatableEntity;
import redmine.model.project.Project;

import java.util.List;
import java.util.Map;

public class ProjectRequests extends CreatableEntity implements Create<Project> {
    @Override
    public void create(Project project) {
        String query = "INSERT INTO public.projects\n" +
                "(id, \"name\", description, homepage, is_public, parent_id, created_on, updated_on, identifier, status," +
                " lft, rgt, inherit_members, default_version_id, default_assigned_to_id)\n" +
                "VALUES(DEFAULT,?,?,?,?,?,?,?,?,?,?,?,?,?,?) RETURNING id;\n";

        List<Map<String, Object>> result = PostgresConnection.INSTANCE.executeQuery(
                query,
                project.getName(),
                project.getDescription(),
                project.getHomepage(),
                project.getIsPublic(),
                project.getParentId(),
                project.getCreatedOn(),
                project.getUpdatedOn(),
                project.getIdentifier(),
                project.getStatus().statusCode,
                project.getLft(),
                project.getRgt(),
                project.getInheritMembers(),
                project.getDefaultVersionId(),
                project.getDefaultAssignedToId()
        );
        Integer id = (Integer) result.get(0).get("id");
        project.setId(id);

    }
}
