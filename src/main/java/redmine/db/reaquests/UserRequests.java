package redmine.db.reaquests;

import lombok.NoArgsConstructor;
import redmine.db.connection.PostgresConnection;
import redmine.model.user.User;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
public class UserRequests implements Create<User>, Delete, Update<User> {

    @Override
    public void create(User user) {
        String query = "INSERT INTO public.users\n" +
                "(id, login, hashed_password, firstname, lastname, \"admin\", status, last_login_on, \"language\"," +
                " auth_source_id, created_on, updated_on, \"type\", identity_url, mail_notification, salt, must_change_passwd," +
                " passwd_changed_on)\n" +
                "VALUES(DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)RETURNING id;\n";

        List<Map<String, Object>> result = PostgresConnection.INSTANCE.executeQuery(
                query,
                user.getLogin(),
                user.getHashedPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getIsAdmin(),
                user.getStatus().statusCode,
                user.getLastLoginOn(),
                user.getLanguage().languageCode,
                user.getAuthSourceId(),
                user.getCreatedOn(),
                user.getUpdatedOn(),
                user.getType(),
                user.getIdentityUrl(),
                user.getMailNotification().name().toLowerCase(),
                user.getSalt(),
                user.getMustChangePassword(),
                user.getPasswordChangedOn()
        );
        Integer userid = (Integer) result.get(0).get("id");
        user.setId(userid);

    }

    @Override
    public void delete(Integer id) {
        String query = "DELETE FROM public.users WHERE id = ?";
        PostgresConnection.INSTANCE.executeQuery(query, id);
    }

    @Override
    public void update(Integer id, User user) {
        String query = "UPDATE public.users\n" +
                "SET login=?, " +
                "hashed_password=?, " +
                "firstname=?, " +
                "lastname=?, " +
                "\"admin\"=?, " +
                "status=?, " +
                "last_login_on=?, " +
                "\"language\"=?, " +
                "auth_source_id=?, " +
                "created_on=?, " +
                "updated_on=?, " +
                "\"type\"=?, " +
                "identity_url=?, " +
                "mail_notification=?, " +
                "salt=?, " +
                "must_change_passwd=?, " +
                "passwd_changed_on=?\n" +
                "WHERE id=?;\n";
        PostgresConnection.INSTANCE.executeQuery(
                query,
                user.getLogin(),
                user.getHashedPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getIsAdmin(),
                user.getStatus().statusCode,
                user.getLastLoginOn(),
                user.getLanguage().languageCode,
                user.getAuthSourceId(),
                user.getCreatedOn(),
                user.getUpdatedOn(),
                user.getType(),
                user.getIdentityUrl(),
                user.getMailNotification().name().toLowerCase(),
                user.getSalt(),
                user.getMustChangePassword(),
                user.getPasswordChangedOn(),
                id
        );
        user.setId(id);
    }
}
