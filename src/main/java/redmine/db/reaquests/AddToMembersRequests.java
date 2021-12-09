package redmine.db.reaquests;


import redmine.db.connection.PostgresConnection;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class AddToMembersRequests {


    public Integer addMember(Integer userId, Integer projectId) {
        String query = "INSERT INTO public.members\n" +
                "(id, user_id, project_id, created_on, mail_notification)\n" +
                "VALUES(DEFAULT, ?, ?, ?, ?) RETURNING id;\n";

        List<Map<String, Object>> result = PostgresConnection.INSTANCE.executeQuery(
                query,
                userId,
                projectId,
                LocalDateTime.now(),
                false
        );
        Integer id = (Integer) result.get(0).get("id");
        return id;
    }


    public void addMemberRole(Integer memberId, Integer roleId) {
        String query = "INSERT INTO public.member_roles\n" +
                "(id, member_id, role_id, inherited_from)\n" +
                "VALUES(DEFAULT, ?, ?, ?);\n";

        PostgresConnection.INSTANCE.executeQuery(
                query,
                memberId,
                roleId,
                null
        );
    }
}

