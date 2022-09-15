package testcases_api;

import io.qameta.allure.Step;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.allure.asserts.AllureAssert;
import redmine.allure.asserts.AllureMethods;
import redmine.api.client.RestApiClient;
import redmine.api.client.RestMethod;
import redmine.api.client.RestRequest;
import redmine.api.client.RestResponse;
import redmine.api.dto.users.Errors;
import redmine.api.dto.users.UserDto;
import redmine.api.dto.users.UserInfoDto;
import redmine.api.rest_assured.GsonProvider;
import redmine.api.rest_assured.RestAssuredClient;
import redmine.api.rest_assured.RestAssuredRequest;
import redmine.db.reaquests.UserRequests;
import redmine.model.user.Status;
import redmine.model.user.Token;
import redmine.model.user.User;
import redmine.utils.StringUtils;

import java.util.Collections;


import static redmine.allure.asserts.AllureAssert.*;
import static redmine.allure.asserts.AllureMethods.*;

public class CrudSystemTest {
    private RestApiClient client;
    private UserInfoDto body;

    @BeforeMethod
    public void getUserAdmin() {
        User userAdmin = new User() {{
            setIsAdmin(true);
            setTokens(Collections.singletonList(new Token(this)));
        }}.create();

        client = createApiClient(new RestAssuredClient(userAdmin));// заранее создал в памяти то что будет отправлять запрос

        body = requestDto(
                new UserInfoDto(
                        new UserDto()
                                .setLogin("Kuznetsov" + StringUtils.randomString("qazwsxedcedcrfvtgbyhnujmiklop", 3))
                                .setFirstName("Mixa" + StringUtils.randomString("qazwsxedcedcrfvtgbyhnujmiklop", 3))
                                .setLastName("Jorg" + StringUtils.randomString("qazwsxedcedcrfvtgbyhnujmiklop", 3))
                                .setMail(StringUtils.randomEmail())
                                .setPassword(StringUtils.randomString("qazwsxedcedcrfvtgbyhnujmiklop12345677890", 8))
                                .setStatus(2)
                )
        );
    }

    @Test(description = "Создание, изменение, получение, удаление пользователя. Администратор системы")
    public void crudTests() {
        Integer userId = createPostUser();
        repeatPostQuery();
        createPostBadEmailAndPass();
        putCreateUser(userId);
        getToGetAUser(userId);
        deleteToDeleteAUser(userId);
        repeatDelete(userId);
    }

    //Step 1. Отправить запрос POST на создание пользователя (данные пользователя должны быть сгенерированы корректно,
    // пользователь должен иметь status = 2)
    @Step("Создание и проверка пользователя")
    private Integer createPostUser() {
        RestRequest request = shapingRequest(new RestAssuredRequest(RestMethod.POST, "/users.json",
                null, null, GsonProvider.GSON.toJson(body)));

        RestResponse response = client.execute(request);
        assertEquals(response.getStatusCode(),
                201,
                "Статус код ответа 201");

        UserDto userResponse = response.getPayload(UserInfoDto.class).getUser();
        assertTrue(userResponse.getId() > 0,
                "Тело ответа содержит данные пользователя, в том числе его id");

        User user = new UserRequests().read(userResponse.getId());
        assertEquals(user.getStatus(),
                Status.UNACCEPTED,
                "В базе данных есть информация о созданном пользователе, status = 2");

        return userResponse.getId();
    }

    @Step("Отправить запрос POST на создание пользователя повторно с тем же телом запроса")
    private void repeatPostQuery() {

        RestRequest request = shapingRequest(new RestAssuredRequest(RestMethod.POST, "/users.json",
                null, null, GsonProvider.GSON.toJson(body)));
        RestResponse response = client.execute(request);
        assertEquals(response.getStatusCode(), 422, "Статус код ответа 422");
        Errors errorsResponse = response.getPayload(Errors.class);
        assertEquals(errorsResponse.getErrors().get(0), "Email уже существует");
        assertEquals(errorsResponse.getErrors().get(1), "Пользователь уже существует");
    }

    @Step("Отправить запрос POST на создание пользователя повторно с тем же телом запроса, \"email\" на невалидный, а \"password\" - 4 символа")
    private void createPostBadEmailAndPass() {
        String pass = body.getUser().getMail();
        String postBox = body.getUser().getMail();

        body.getUser().setMail("SDET.ru");
        body.getUser().setPassword("edcr");

        RestRequest request = shapingRequest(new RestAssuredRequest(RestMethod.POST, "/users.json", null,
                null, GsonProvider.GSON.toJson(body)));
        RestResponse response = client.execute(request);
        assertEquals(response.getStatusCode(), 422, "Статус код ответа 422");
        Errors checkErrorThirdStep = response.getPayload(Errors.class);
        assertEquals(checkErrorThirdStep.getErrors().get(0), "Email имеет неверное значение");
        assertEquals(checkErrorThirdStep.getErrors().get(1), "Пользователь уже существует");
        assertEquals(checkErrorThirdStep.getErrors().get(2), "Пароль недостаточной длины (не может быть меньше 8 символа)");
        body.getUser().setMail(postBox);
        body.getUser().setPassword(pass);
    }


    @Step("Отправка запроса PUT из шага №1 на изменение пользователя, изменить поле status = 1")
    private void putCreateUser(Integer userId) {

        body.getUser().setStatus(1);
        RestRequest request = shapingRequest(new RestAssuredRequest(RestMethod.PUT, "/users/" + userId + ".json",
                null, null, GsonProvider.GSON.toJson(body)));
        RestResponse response = client.execute(request);
        assertEquals(response.getStatusCode(), 204, "Статус код ответа 204");
        User user2 = new UserRequests().read(userId);
        assertEquals(user2.getStatus(), Status.ACTIVE, " Изменен status = 1");
    }

    @Step("Отправить запрос GET на получение пользователя")
    private void getToGetAUser(Integer userId) {
        RestRequest request = shapingRequest(new RestAssuredRequest(RestMethod.GET, "/users/" + userId + ".json",
                null, null, GsonProvider.GSON.toJson(body)));
        RestResponse response = client.execute(request);
        assertEquals(response.getStatusCode(), 200, "Статус код ответа 200");
        UserDto userResponse = userDtoFromResponse(response.getPayload(UserInfoDto.class).getUser());
        assertEquals(new UserRequests().read(userResponse.getId()).getStatus(), Status.ACTIVE);
    }

    @Step("Отправить запрос DELETE на удаление пользователя")
    private void deleteToDeleteAUser(Integer userid) {
        RestRequest request = shapingRequest(new RestAssuredRequest(RestMethod.DELETE, "/users/" + userid + ".json",
                null, null, GsonProvider.GSON.toJson(body)));
        RestResponse response = client.execute(request);
        assertEquals(response.getStatusCode(), 204, "Статус код ответа 200");
        assertNull(new UserRequests().read(userid), "В базе данных отсутствует информация о ранее созданном пользователе");
    }

    @Step("Отправить запрос DELETE на удаление пользователя (повторно)")
    private void repeatDelete(Integer userId) {
        RestRequest request = shapingRequest(new RestAssuredRequest(RestMethod.DELETE, "/users/" + userId + ".json", null, null,
                GsonProvider.GSON.toJson(body)));
        RestResponse response = client.execute(request);
        assertEquals(response.getStatusCode(), 404, "Статус код ответа 404");
    }
}
