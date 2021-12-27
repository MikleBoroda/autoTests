package testcases_api;


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

import static redmine.allure.asserts.AllureAssert.*;
import static redmine.allure.asserts.AllureMethods.*;

public class GetUserWithOutAdmin {
    private User firstUser;
    private User secondUser;
    private RestApiClient client;
    private RestRequest request;


    @BeforeMethod(description = "Заведен пользователь в системе. У пользователя есть доступ к API и ключ API. Заведен еще один пользователь в системе")
    public void prepareFixtures() {
        firstUser = new User() {{
            setTokens(Collections.singletonList(new Token(this)));
        }}.create();

        secondUser = new User().create();

        client = createApiClient(new RestAssuredClient(firstUser));


    }

    @Test(description = "Получение пользователей. Пользователь без прав администратора")
    public void getTwoUsers() {
        //Отправить запрос GET на получение пользователя из п.1, используя ключ API из п.2
        request = shapingRequest(new RestAssuredRequest(RestMethod.GET, "/users/" + firstUser.getId() + ".json",
                null, null, null));
        RestResponse response = client.execute(request);
        assertEquals(response.getStatusCode(), 200, " Статус код ответа 200");

        UserInfoDto responseFirstUser = requestDto(response.getPayload(UserInfoDto.class));

        assertFalse(responseFirstUser.getUser().getAdmin(), "Присутсвует admin = false");
        assertEquals(responseFirstUser.getUser().getApiKey(), firstUser.getTokens().get(0).getValue());


        //Отправить запрос GET на получения пользователя из п.3, используя ключ API из п.2
        // т к в client передается пользователь уже с доступом к api_key, в запросе просто указываем Id  второго пользователя
        request = shapingRequest(new RestAssuredRequest(RestMethod.GET, "/users/" + secondUser.getId() + ".json",
                null, null, null));
        response = client.execute(request);
        assertEquals(response.getStatusCode(), 200, "Статус код ответа 200");

        UserInfoDto responseSecondUser = response.getPayload(UserInfoDto.class);

        assertNull(responseSecondUser.getUser().getAdmin(), "поле \"admin\" в ответе не содержится");
        assertNull(responseSecondUser.getUser().getApiKey(), "поле \"api_key\" в ответе не содержится");

    }
}
