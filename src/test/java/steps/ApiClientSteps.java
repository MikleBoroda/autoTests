package steps;

import cucumber.api.java.bg.И;
import cucumber.api.java.ru.Затем;
import redmine.allure.asserts.AllureMethods;
import redmine.api.client.RestApiClient;
import redmine.api.rest_assured.RestAssuredClient;
import redmine.context.Context;
import redmine.model.user.User;

public class ApiClientSteps {

    @И("Создан api-клент {string} для пользователя {string}")
    public void createApiClient(String apiClientStashId, String userStashId) {
        User user = Context.getStash().get(userStashId, User.class);
        RestApiClient apiClient = AllureMethods.createApiClient(new RestAssuredClient(user));
        Context.getStash().put(apiClientStashId, apiClient);
    }


}
