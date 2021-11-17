package testcases_api;

import org.checkerframework.checker.units.qual.A;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.api.client.RestApiClient;
import redmine.api.client.RestMethod;
import redmine.api.client.RestRequest;
import redmine.api.client.RestResponse;
import redmine.api.dto.users.UserInfoDto;
import redmine.api.rest_assured.RestAssuredClient;
import redmine.api.rest_assured.RestAssuredRequest;
import redmine.db.reaquests.UserRequests;
import redmine.model.user.Token;
import redmine.model.user.User;

import java.util.Collections;

public class DeleteUsersWithOutAdmin {
    private User firstUser;
    private User secondUser;
    private RestApiClient client;
    private RestRequest request;

    /*
    Предусловие:
    1. Заведен пользователь в системе
    2. У пользователя есть доступ к API и ключ API
    3. Заведен еще один пользователь в системе
     */

    @BeforeMethod
    public void prepareFixtures() {
        firstUser = new User() {{
            setTokens(Collections.singletonList(new Token(this)));
        }}.create();

        secondUser = new User().create();

        client = new RestAssuredClient(firstUser);
    }

    @Test
    public void deleteTwoUsers() {
        //1. Отправить запрос DELETE на удаление пользователя из п.3, используя ключ из п.2. (удаление другого пользователя)
        request = new RestAssuredRequest(RestMethod.DELETE, "/users/" + secondUser.getId() + ".json", null, null, null);
        RestResponse response = client.execute(request);
        Assert.assertEquals(response.getStatusCode(), 403);
        Assert.assertNotNull(new UserRequests().read(secondUser.getId()));

        //2. Отправить запрос DELETE на удаление пользователя из п.1, используя ключи из п.2 (удаление себя)
        request = new RestAssuredRequest(RestMethod.DELETE, "/users/" + firstUser.getId() + ".json", null, null, null);
        response = client.execute(request);
        Assert.assertEquals(response.getStatusCode(), 403);
        Assert.assertNotNull(new UserRequests().read(firstUser.getId()));


    }
}
