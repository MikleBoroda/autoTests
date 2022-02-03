package steps;

import cucumber.api.java.ru.Затем;
import cucumber.api.java.ru.И;
import cucumber.api.java.ru.Тогда;
import redmine.allure.asserts.AllureAssert;
import redmine.api.client.RestApiClient;
import redmine.api.client.RestMethod;
import redmine.api.client.RestRequest;
import redmine.api.client.RestResponse;
import redmine.api.dto.users.Errors;
import redmine.api.dto.users.UserDto;
import redmine.api.dto.users.UserInfoDto;
import redmine.api.rest_assured.GsonProvider;
import redmine.api.rest_assured.RestAssuredRequest;
import redmine.context.Context;
import redmine.db.reaquests.UserRequests;
import redmine.model.user.Email;
import redmine.model.user.Status;
import redmine.model.user.User;

import java.util.Collections;

import static redmine.allure.asserts.AllureMethods.*;

public class ApiSteps {
    @Тогда("Отправить {string} запрос через {string} на эндпоинт {string} для создания пользователя в БД и получить {string}")
    public void sendRequestPost(String requestType, String clientId, String endPoint, String responseId) {
        User user = new User() {{
            setEmails(Collections.singletonList(new Email()));
            setStatus(Status.UNACCEPTED);
        }};
        UserInfoDto body = getUserInfoDto(user);
        RestRequest request =
                new RestAssuredRequest(
                        RestMethod.POST,
                        endPoint,
                        null,
                        null,
                        GsonProvider.GSON.toJson(body)
                );
        RestApiClient apiClient = Context.getStash().get(clientId, RestApiClient.class);
        RestResponse response = apiClient.execute(request);

        Context.getStash().put(clientId, apiClient);
        Context.getStash().put(requestType, request);
        Context.getStash().put(responseId, response);
    }

    @Затем("Повторить запрос {string} через api-client {string} на создание пользователя с тем же телом запроса проверить" +
            " ответ {string}")
    public void repeatRequestPost(String requestType, String apiClientStash, String responseStash) {
        RestRequest request = Context.getStash().get(requestType, RestRequest.class);
        RestApiClient apiClient = Context.getStash().get(apiClientStash, RestApiClient.class);
        RestResponse response = apiClient.execute(request);
        Context.getStash().put(responseStash, response);
    }

    @Затем("Повторить запрос POST через api-client {string} на создание пользователя с тем же телом запроса,указать" +
            " невалидный E-mail и password и получить ответ {string}")
    public void postRequestBadEmailAndPassword(String apiClientStash, String responseStash) {
        User user = new User() {{
            setEmails(Collections.singletonList(new Email()));
            setStatus(Status.UNACCEPTED);
        }};

        UserInfoDto body = getUserInfoDto(user);
        body.getUser().setMail("Sdet.ru");
        body.getUser().setPassword("weqr");

        RestRequest request =
                new RestAssuredRequest(
                        RestMethod.POST,
                        " /users.json",
                        null,
                        null,
                        GsonProvider.GSON.toJson(body)
                );
        RestApiClient apiClient = Context.getStash().get(apiClientStash, RestApiClient.class);
        RestResponse response = apiClient.execute(request);

        Context.getStash().put(responseStash, response);
    }

    @Затем("Отправить запрос PUT запрос через api-Client {string} на эндпоинт {string} используя ответ {string} из шага №1," +
            "изменить status = {int}, проверить ответ {string}")
    public void sendRequestPut(String apicClientStash, String endPoint, String responseStash, Integer statusCode, String responseCheck) {
        RestResponse response = Context.getStash().get(responseStash, RestResponse.class);
        UserInfoDto body = response.getPayload(UserInfoDto.class);
        body.getUser().setStatus(statusCode);
        RestRequest request =
                new RestAssuredRequest(
                        RestMethod.PUT,
                        String.format(endPoint, body.getUser().getId()),
                        null,
                        null,
                        GsonProvider.GSON.toJson(body)
                );
        RestApiClient apiClient = Context.getStash().get(apicClientStash, RestApiClient.class);
        response = apiClient.execute(request);
        Context.getStash().put(responseCheck, response);
    }

    @Затем("Отправить запрос GET через api-client {string} пользователем {string} на эндпоинт {string}," +
            " получить ответ = {string}")
    public void sendRequestGet(String apiClientStash, String userStash, String endPoint, String responseCheck) {
        User user = Context.getStash().get(userStash, User.class);
        RestRequest request =
                new RestAssuredRequest(
                        RestMethod.GET,
                        String.format(endPoint, user.getId()),
                        null,
                        null,
                        null
                );
        RestApiClient apiClient = Context.getStash().get(apiClientStash, RestApiClient.class);
        RestResponse response = apiClient.execute(request);
        Context.getStash().put(responseCheck, response);
    }

    @Затем("Отправить запрос DELETE через api-client {string} на удаление  пользователя {string} на эндпоинт {string}, получить ответ = {string}")
    public void sendRequestDelete(String apiClientStash, String userStash, String endPoint, String responseCheck) {
        User user = Context.getStash().get(userStash, User.class);
        RestRequest request = new RestAssuredRequest(RestMethod.DELETE, String.format(endPoint, user.getId()), null, null, null);
        RestApiClient apiClient = Context.getStash().get(apiClientStash, RestApiClient.class);
        RestResponse response = apiClient.execute(request);
        Context.getStash().put(responseCheck, response);
    }

    @И("Проверить, что статус код из ответа {string} = {int}")
    public void assertCheckStatusCode(String responseId, Integer statusCode) {
        RestResponse response = Context.getStash().get(responseId, RestResponse.class);
        AllureAssert.assertEquals(response.getStatusCode(), statusCode);
    }

    @И("Проверить, что тело ответа {string} содержит данные пользователя в том числе его id")
    public void assertDataValidation(String responseBody) {
        RestResponse response = Context.getStash().get(responseBody, RestResponse.class);
        UserDto userResponse = userDtoFromResponse(response.getPayload(UserInfoDto.class).getUser());
        AllureAssert.assertTrue(userResponse.getId() > 0);
    }

    @И("Проверить, что статус пользователя {string} из ответа {string} такойже как в БД status = {int}")
    public void assertCheckStatus(String userStash, String responseBody, Integer status) {
        RestResponse response = Context.getStash().get(responseBody, RestResponse.class);
        UserDto userResponse = userDtoFromResponse(response.getPayload(UserInfoDto.class).getUser());
        User user = new UserRequests().read(userResponse.getId());
        AllureAssert.assertEquals(user.getStatus().statusCode, status);
        Context.getStash().put(userStash, user);
    }

    @И("Проверить, что тело ответа {string} содержит error, в теле содржится что {string} и {string}")
    public void assertError(String responseStash, String emailExist, String userExist) {
        RestResponse response = Context.getStash().get(responseStash, RestResponse.class);
        Errors errors = response.getPayload(Errors.class);
        AllureAssert.assertEquals(errors.getErrors().get(0), emailExist);
        AllureAssert.assertEquals(errors.getErrors().get(1), userExist);
    }

    @И("Проверить, что пользователь {string} отсутствует в БД")
    public void assertAbsenceCheckUser(String userStash) {
        User user = Context.getStash().get(userStash, User.class);
        AllureAssert.assertNull(user);
    }

    @И("Проверить, что пользователь {string} есть в БД")
    public void assertPresenceCheckUser(String userStash) {
        User user = Context.getStash().get(userStash, User.class);
        User userFromDb = new UserRequests().read(user.getId());
        AllureAssert.assertNotNull(userFromDb);
    }

    @И("Проверить,что в ответе {string} для пользователя {string} есть поля admin:true и api_key: ключ API")
    public void assertFieldValidation(String responseStash, String userStash) {
        User user = Context.getStash().get(userStash, User.class);
        RestResponse response = Context.getStash().get(responseStash, RestResponse.class);
        UserInfoDto responseUser = requestDto(response.getPayload(UserInfoDto.class));
        AllureAssert.assertFalse(responseUser.getUser().getAdmin());
        AllureAssert.assertEquals(responseUser.getUser().getApiKey(), user.getTokens().get(0).getValue());
    }

    @И("Проверить,что в ответе {string} для пользователя отсутствуют поля admin и api_key")
    public void assetNoFields(String responseStash) {
        RestResponse response = Context.getStash().get(responseStash, RestResponse.class);
        UserInfoDto responseUser = requestDto(response.getPayload(UserInfoDto.class));
        AllureAssert.assertNull(responseUser.getUser().getAdmin());
        AllureAssert.assertNull(responseUser.getUser().getApiKey());
    }

    private UserInfoDto getUserInfoDto(User user) {

        return requestDto(new UserInfoDto(
                        new UserDto()
                                .setLogin(user.getLogin())
                                .setPassword(user.getPassword())
                                .setFirstName(user.getFirstName())
                                .setLastName(user.getLastName())
                                .setMail(user.getEmails().get(0).getAddress())
                                .setStatus(user.getStatus().statusCode)
                )
        );
    }
}
