package redmine.model.user;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import redmine.model.Creatable;
import redmine.model.CreatableEntity;

import static redmine.model.StringUtils.randomHexString;

@Getter
@Setter
@Accessors(chain = true)
public class Token extends CreatableEntity implements Creatable<Token> {

    private Integer userid;
    private TokenType action = TokenType.API; //api || feeds || session
    private String value = randomHexString(16);

    Token(User user) {

        this.userid = user.id;
    }

    @Override
    public Token create() {
        //todo: реализовать через базу SQl- запросы
        return null;
    }


    public enum TokenType {
        API,
        FEEDS,
        SESSION
    }
}
