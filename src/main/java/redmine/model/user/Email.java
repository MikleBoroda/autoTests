package redmine.model.user;

import io.qameta.allure.Step;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import redmine.db.reaquests.EmailRequests;
import redmine.model.Creatable;
import redmine.model.CreatableEntity;

import static redmine.utils.StringUtils.randomEmail;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class Email extends CreatableEntity implements Creatable<Email> {
    private Integer userId;
    private String address = randomEmail();
    private Boolean isDefault = true;
    private Boolean notify = true;

    public Email(User user) {
        this.userId = user.id;
        user.getEmails().add(this);
    }

    @Override
    @Step("Создан Email В БД")
    public Email create() {
        new EmailRequests().create(this);
        return this;
    }
}
