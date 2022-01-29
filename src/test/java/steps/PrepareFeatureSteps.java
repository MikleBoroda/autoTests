package steps;

import redmine.cucmber.validators.ProjectParametersValidator;
import redmine.cucmber.validators.RoleParametersValidator;
import redmine.cucmber.validators.UserParametersValidator;
import cucumber.api.java.ru.Пусть;
import io.cucumber.datatable.DataTable;
import redmine.context.Context;
import redmine.model.project.Project;
import redmine.model.role.Permissions;
import redmine.model.role.Role;
import redmine.model.user.*;

import java.util.*;


import static java.util.stream.Collectors.toList;

public class PrepareFeatureSteps {

    @Пусть("Имеется список E-Mail адресов {string}:")
    public void createAddresses(String emailStashId, DataTable data) {
        List<Map<String, String>> maps = data.asMaps();
        List<Email> emails = new ArrayList<>();
        maps.forEach(map -> {
            String address = map.get("Адрес");
            Boolean isDefault = Boolean.parseBoolean(map.get("По умолчанию"));
            Boolean notify = Boolean.parseBoolean(map.get("Уведомление"));
            Email email = new Email()
                    .setAddress(address)
                    .setIsDefault(isDefault)
                    .setNotify(notify);
            emails.add(email);
        });
        Context.getStash().put(emailStashId, emails);

    }

    @Пусть("В системе создан пользователь {string} со следующими параметрами:")
    public void createUser(String stashId, Map<String, String> parameters) {
        UserParametersValidator.validateUserParameters(parameters.keySet());
        User user = new User();
        if (parameters.containsKey("Администратор")) {
            user.setIsAdmin(Boolean.valueOf(parameters.get("Администратор")));
        }
        if (parameters.containsKey("Статус")) {
            String description = parameters.get("Статус");
            Status status = Status.of(description);
            user.setStatus(status);
        }
        if (parameters.containsKey("Уведомление о новых событиях")) {
            String description = parameters.get("Уведомление о новых событиях");
            MailNotification mailNotification = MailNotification.of(description);
            user.setMailNotification(mailNotification);
        }
        if (parameters.containsKey("E-Mail")) {
            String emailStashId = parameters.get("E-Mail");
            List<Email> emails = Context.getStash().get(emailStashId, List.class);
            user.setEmails(emails);
        }
        if (parameters.containsKey("Api-ключ")) {
            user.setTokens(Collections.singletonList(new Token(user)));
        }
        user.create();

        Context.getStash().put(stashId, user);
    }

    @Пусть("Существует проект {string} с параметрами")
    public void createProject(String projectStash, Map<String, String> parameters) {
        ProjectParametersValidator.validateProjectParameters(parameters.keySet());
        Project project;
        if (parameters.containsValue("false")) {
            project = new Project() {{
                setIsPublic(false);
            }}.create();
        } else {
            project = new Project().create();
        }
        Context.getStash().put(projectStash, project);
    }

    @Пусть("В системе существует роль {string} с правами:")
    public void createRole(String roleStash, List<String> permissions) {
        RoleParametersValidator.validateRoleParameters(permissions);
        Role role = new Role();
        role.setPermissionsList(
                permissions.stream().map(description -> Permissions.off(description)).collect(toList())
        );
        role.create();
        Context.getStash().put(roleStash, role);
    }

    //Создаем список ролей для того чтобы потом связать с пользователем
    @Пусть("Список ролей {string} содержит роли:")
    public void createRoleList(String roleStash, List<String> roleListStashId) {
        List<Role> roles = new ArrayList<>();
        for (String roleListStashIds : roleListStashId) {
            Role role = Context.getStash().get(roleListStashIds, Role.class);
            roles.add(role);
        }
        Context.getStash().put(roleStash, roles);
    }

    @Пусть("Пользователь {string} имеет доступ к проекту {string} со списком ролей {string}")
    public void userHaveProject(String userStash, String projectStash, String roleStash) {
        List<Role> roles = Context.getStash().get(roleStash, List.class);
        Project project = Context.getStash().get(projectStash, Project.class);
        User user = Context.getStash().get(userStash, User.class);

        user.addProjectAndRoles(project, roles);
    }
}
