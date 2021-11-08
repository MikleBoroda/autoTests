package testcases_api;

import com.google.gson.Gson;
import io.restassured.response.Response;
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
import redmine.model.user.Status;
import redmine.model.user.Token;
import redmine.model.user.User;


import java.util.Collections;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CrudUserAdminSystemTest {
    private RestApiClient client;
    private RestRequest request;
    private User userAdmin;
    private UserInfoDto body;


    @BeforeMethod
    public void getUserAdmin() {
        userAdmin = new User() {{
            setIsAdmin(true);
            setTokens(Collections.singletonList(new Token(this)));
        }}.create();

        client = new RestAssuredClient(userAdmin);// заранее создал в памяти то что будет отправлять запрос

        /**
         * UserInfoDto - структура json
         * UserDto - описание объектов json
         */
        body = new UserInfoDto(
                new UserDto()
                        .setLogin("Kuznetsov" + StringUtils.randomString("qazwsxedcedcrfvtgbyhnujmiklop", 3))
                        .setFirstname("Mixa" + StringUtils.randomString("qazwsxedcedcrfvtgbyhnujmiklop", 3))
                        .setLastName("Jorg" + StringUtils.randomString("qazwsxedcedcrfvtgbyhnujmiklop", 3))
                        .setMail(StringUtils.randomEmail())
                        .setPassword(StringUtils.randomString("qazwsxedcedcrfvtgbyhnujmiklop12345677890", 8))

        );
    }


    private Integer createPostUser() {
        RestRequest request = new RestAssuredRequest(RestMethod.POST, "/users.json", null, null, GsonProvider.GSON.toJson(body));
        RestResponse response = client.execute(request);
        UserDto userRespones = response.getPayload(UserInfoDto.class).getUser();

        assertEquals(response.getStatusCode(), 201);
        assertTrue(userRespones.getId() > 0);

        return userRespones.getId();

    }


    @Test
    public void moveCrudTest() {
        createPostUser();



    }


}
