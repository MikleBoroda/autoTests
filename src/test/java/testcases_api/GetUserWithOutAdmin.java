package testcases_api;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.api.client.RestApiClient;
import redmine.api.client.RestMethod;
import redmine.api.client.RestRequest;
import redmine.api.client.RestResponse;
import redmine.api.dto.users.UserDto;
import redmine.api.dto.users.UserInfoDto;
import redmine.api.rest_assured.RestAssuredClient;
import redmine.api.rest_assured.RestAssuredRequest;
import redmine.model.user.Token;
import redmine.model.user.User;

import java.util.Collections;

public class GetUserWithOutAdmin {
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
    public void getTwoUsers() {
        //Отправить запрос GET на получение пользователя из п.1, используя ключ API из п.2
        request = new RestAssuredRequest(RestMethod.GET, "/users/" + firstUser.getId() + ".json", null, null, null);
        RestResponse response = client.execute(request);
        Assert.assertEquals(response.getStatusCode(), 200);

        UserInfoDto responseFirstUser = response.getPayload(UserInfoDto.class);

        Assert.assertFalse(responseFirstUser.getUser().getAdmin());
        Assert.assertEquals(responseFirstUser.getUser().getApiKey(), firstUser.getTokens().get(0).getValue());


        //Отправить запрос GET на получения пользователя из п.3, используя ключ API из п.2
        // т к в client передается пользователь уже с доступом к api_key, в запросе просто указываем Id  второго пользователя
        request = new RestAssuredRequest(RestMethod.GET, "/users/" + secondUser.getId() + ".json", null, null, null);
        response = client.execute(request);
        Assert.assertEquals(response.getStatusCode(), 200);

        UserInfoDto responseSecondUser = response.getPayload(UserInfoDto.class);

        Assert.assertNull(responseSecondUser.getUser().getAdmin());
        Assert.assertNull(responseSecondUser.getUser().getApiKey());

    }
}
