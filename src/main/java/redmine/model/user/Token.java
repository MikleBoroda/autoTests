package redmine.model.user;

import io.qameta.allure.Step;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import redmine.db.reaquests.TokenRequests;
import redmine.model.Creatable;
import redmine.model.CreatableEntity;

import static redmine.utils.StringUtils.randomHexString;

@Getter
@Setter
@Accessors(chain = true)
public class Token extends CreatableEntity implements Creatable<Token> {

    private Integer userid;
    private TokenType action = TokenType.API;
    private String value = randomHexString(16);

    public Token(User user) {
        this.userid = user.id;
        user.getTokens().add(this);
    }

    @Override
    @Step("Создан токен в БД")
    public Token create() {
        new TokenRequests().create(this);
        return this;
    }


    public enum TokenType {
        API,
        FEEDS,
        SESSION
    }
}
