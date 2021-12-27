package testcases_api;


import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.api.client.RestApiClient;
import redmine.api.client.RestMethod;
import redmine.api.client.RestRequest;
import redmine.api.client.RestResponse;
import redmine.api.rest_assured.RestAssuredClient;
import redmine.api.rest_assured.RestAssuredRequest;
import redmine.db.reaquests.UserRequests;
import redmine.model.user.Token;
import redmine.model.user.User;

import java.util.Collections;

import static redmine.allure.asserts.AllureAssert.*;
import static redmine.allure.asserts.AllureMethods.*;

public class DeleteUsersWithOutAdmin {
    private User firstUser;
    private User secondUser;
    private RestApiClient client;
    private RestRequest request;


    @BeforeMethod(description = "Заведен пользователь в системе." +
            "У пользователя есть доступ к API и ключ API." +
            " Заведен еще один пользователь в системе ")
    public void prepareFixtures() {
        firstUser = new User() {{
            setTokens(Collections.singletonList(new Token(this)));
        }}.create();

        secondUser = new User().create();

        client = createApiClient(new RestAssuredClient(firstUser));
    }

    @Test(description = "Удаление пользователей. Пользователь без прав администратора")
    public void deleteTwoUsers() {
        //1. Отправить запрос DELETE на удаление пользователя из п.3, используя ключ из п.2. (удаление другого пользователя)
        request = shapingRequest(new RestAssuredRequest(RestMethod.DELETE, "/users/" + secondUser.getId() + ".json",
                null, null, null));
        RestResponse response = client.execute(request);
        assertEquals(response.getStatusCode(), 403, "Статус код ответа 403");
        assertNotNull(new UserRequests().read(secondUser.getId()), " В базе данных присутствует информация о пользователе из п.3. предусловия");

        //2. Отправить запрос DELETE на удаление пользователя из п.1, используя ключи из п.2 (удаление себя)
        request = shapingRequest(new RestAssuredRequest(RestMethod.DELETE, "/users/" + firstUser.getId() + ".json",
                null, null, null));
        response = client.execute(request);
        assertEquals(response.getStatusCode(), 403, "Статус код ответа 403");
        assertNotNull(new UserRequests().read(firstUser.getId()), "В базе данных присутствует информация о пользователе из п.1. предусловия");

    }
}
