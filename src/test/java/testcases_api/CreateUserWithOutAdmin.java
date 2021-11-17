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
import redmine.api.rest_assured.GsonProvider;
import redmine.api.rest_assured.RestAssuredClient;
import redmine.api.rest_assured.RestAssuredRequest;
import redmine.model.StringUtils;
import redmine.model.user.Token;
import redmine.model.user.User;

import java.util.Collections;

public class CreateUserWithOutAdmin {

    private RestApiClient client;
    private RestRequest request;
    private User userNotAdmin;
    private UserInfoDto bodyDto;

    @BeforeMethod

    public void prepareFixtures() {

        userNotAdmin = new User() {{
            setTokens(Collections.singletonList(new Token(this)));
        }}.create();

        client = new RestAssuredClient(userNotAdmin);

        bodyDto = new UserInfoDto(
                new UserDto()
                        .setLogin("Kuznetsov" + StringUtils.randomString("qazwsxedcedcrfvtgbyhnujmiklop", 3))
                        .setFirstName("Mixa" + StringUtils.randomString("qazwsxedcedcrfvtgbyhnujmiklop", 3))
                        .setLastName("Jorg" + StringUtils.randomString("qazwsxedcedcrfvtgbyhnujmiklop", 3))
                        .setMail(StringUtils.randomEmail())
                        .setPassword(StringUtils.randomString("qazwsxedcedcrfvtgbyhnujmiklop12345677890", 8))
                        .setStatus(1)
        );

        request = new RestAssuredRequest(RestMethod.POST, "/users.json", null, null, GsonProvider.GSON.toJson(bodyDto));
    }

    @Test
    public void apiPostUserNotAdmin() {
        RestResponse response = client.execute(request);
        Assert.assertEquals(response.getStatusCode(), 403);

    }
}
