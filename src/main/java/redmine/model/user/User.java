package redmine.model.user;

import io.qameta.allure.Step;
import lombok.*;
import lombok.experimental.Accessors;
import redmine.db.reaquests.UserRequests;
import redmine.model.Creatable;
import redmine.model.CreatableEntity;
import redmine.model.Entity;
import redmine.model.project.Project;
import redmine.model.role.Role;

import static org.apache.commons.codec.digest.DigestUtils.sha1Hex;
import static redmine.utils.StringUtils.randomHexString;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class User extends CreatableEntity implements Creatable<Entity> {
    private String login = "AutoLogin" + randomHexString(10);
    private String password = "1qaz@WSX";
    private String salt = randomHexString(32);
    private String hashedPassword = getHashedPassword();
    private String firstName = "AutoF" + randomHexString(10);
    private String lastName = "AutoL" + randomHexString(10);
    private Boolean isAdmin = false;
    private Status status = Status.ACTIVE;
    private LocalDateTime lastLoginOn;
    private Language language = Language.RUSSIAN;
    private String authSourceId;
    private String type = "User";
    private String identityUrl;
    private MailNotification mailNotification = MailNotification.NONE;
    private Boolean mustChangePassword = false;
    private LocalDateTime passwordChangedOn;
    private List<Token> tokens = new ArrayList<>();
    private List<Email> emails = new ArrayList<>();
    private Map<Project, List<Role>> projectsMap = new HashMap<>();

    @Step("Добавлен проект с правами")
    public void addProjectAndRoles(Project project, List<Role> roles) {
        projectsMap.put(project, roles);
    }

    public String getHashedPassword() {
        return sha1Hex(salt + sha1Hex(password));
    }

    @Override
    @Step("Создан пользователь в БД")
    public User create() {
        new UserRequests().create(this);
        tokens.forEach(t -> t.setUserid(id));
        emails.forEach(e -> e.setUserId(id));
        tokens.forEach(Token::create);
        emails.forEach(Email::create);
        return this;
    }
}
