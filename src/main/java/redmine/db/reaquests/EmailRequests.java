package redmine.db.reaquests;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import redmine.db.connection.PostgresConnection;
import redmine.model.user.Email;
import redmine.model.user.User;

import java.util.List;
import java.util.Map;

@NoArgsConstructor

public class EmailRequests implements Create<Email> {

    @Override
    public void create(Email email) {
        String query = "INSERT INTO public.email_addresses\n" +
                "(id, user_id, address, is_default, \"notify\", created_on, updated_on)\n" +
                "VALUES(DEFAULT, ?, ?, ?, ?, ?, ?) RETURNING id;\n";


        List<Map<String, Object>> result = PostgresConnection.INSTANCE.executeQuery(
                query,
                email.getUserId(),
                email.getAddress(),
                email.getIsDefault(),
                email.getNotify(),
                email.getCreatedOn(),
                email.getUpdatedOn()
        );


        Integer emailId = (Integer) result.get(0).get("id"); //в первом столбце в первой строке получим Id
        email.setId(emailId);//емейлу присваиваем  Id


    }
}
