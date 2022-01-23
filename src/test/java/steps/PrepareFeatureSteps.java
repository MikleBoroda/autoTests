package steps;

import redmine.cucmber.validators.UserParametersValidator;
import cucumber.api.java.ru.Пусть;
import io.cucumber.datatable.DataTable;
import redmine.context.Context;
import redmine.model.user.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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

}
