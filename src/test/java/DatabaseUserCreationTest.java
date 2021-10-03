import org.testng.annotations.Test;
import redmine.model.user.Email;
import redmine.model.user.Token;
import redmine.model.user.User;

import java.util.Random;

public class DatabaseUserCreationTest {
    @Test
    public void userDatabaseCreationTests() {
        User user = new User();
        user.setPassword("qazWSXedc");
        user.setFirstName("Имя" + new Random().nextInt(100));
        user.setLastName("Фамилия" + new Random().nextInt(100));

        Token token = new Token(user);
        token.setAction(Token.TokenType.API);

        Email email1 = new Email(user);
        Email email2 = new Email(user);
        email2.setIsDefault(false);
        Email email3 = new Email(user);
        email3.setIsDefault(false);
        Email email4 = new Email(user);
        email4.setAddress("ManualTesterMixa@SDET.ru");
        email4.setIsDefault(false);

        user.create();

        System.out.println(user.getLogin());
        System.out.println(token.getValue());
        System.out.println(email1.getAddress());
        System.out.println(email2.getAddress());
        System.out.println(email3.getAddress());

    }
}