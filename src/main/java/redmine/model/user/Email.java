package redmine.model.user;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import redmine.db.reauests.EmailRequests;
import redmine.model.Creatable;
import redmine.model.CreatableEntity;

import static redmine.model.StringUtils.randomEmail;

@Getter
@Setter
@Accessors(chain = true)
public class Email extends CreatableEntity implements Creatable<Email> {
    private Integer userid;
    private String address = randomEmail();
    private Boolean isDefault = true;
    private Boolean notify = true;

    Email(User user) {
        this.userid = user.id;
        user.getEmails().add(this);
    }

    @Override
    public Email create() {
        new EmailRequests().create(this);
        return this;
    }
}
