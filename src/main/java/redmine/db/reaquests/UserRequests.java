package redmine.db.reaquests;

import lombok.NoArgsConstructor;
import redmine.db.connection.PostgresConnection;
import redmine.model.user.Status;
import redmine.model.user.User;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
public class UserRequests implements Create<User>, Delete, Update<User>, Read<User> {

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
        Integer userId = (Integer) result.get(0).get("id");
        user.setId(userId);

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


    @Override
    public User read(Integer id) {
        String query = "SELECT * FROM public.users WHERE id=?;\n";
        List<Map<String, Object>> result = PostgresConnection.INSTANCE.executeQuery(query, id);
        User user = new User();

        if (result.size() != 0) {

            user.setId((Integer) result.get(0).get("id"));
            user.setStatus(Status.getIntStatus((Integer) result.get(0).get("status")));
            return user;
        }
        return null;
    }

    public User read(String login) {
        String query = "SELECT * FROM users WHERE login = ?";
        List<Map<String, Object>> result = PostgresConnection.INSTANCE.executeQuery(query, login);
        User user = new User();
        user.setLogin((String) result.get(0).get("login"));

        return user;


    }


}

