import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
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
import redmine.utils.StringUtils;
import redmine.model.user.Status;
import redmine.model.user.Token;
import redmine.model.user.User;


import java.util.Collections;


import static org.testng.Assert.*;

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

        body = new UserInfoDto(
                new UserDto()
                        .setLogin("Kuznetsov" + StringUtils.randomString("qazwsxedcedcrfvtgbyhnujmiklop", 3))
                        .setFirstName("Mixa" + StringUtils.randomString("qazwsxedcedcrfvtgbyhnujmiklop", 3))
                        .setLastName("Jorg" + StringUtils.randomString("qazwsxedcedcrfvtgbyhnujmiklop", 3))
                        .setMail(StringUtils.randomEmail())
                        .setPassword(StringUtils.randomString("qazwsxedcedcrfvtgbyhnujmiklop12345677890", 8))
                        .setStatus(2)

        );
    }


    @Test
    public void moveCrudTest() {

        //Step  Отправить запрос POST на создание пользователя (данные пользователя должны быть сгенерированы корректно,
        // пользователь должен иметь status = 2)
        request = new RestAssuredRequest(RestMethod.POST, "/users.json", null, null, GsonProvider.GSON.toJson(body));

        RestResponse response = client.execute(request);
        assertEquals(response.getStatusCode(), 201);

        UserDto userResponse = response.getPayload(UserInfoDto.class).getUser();
        assertTrue(userResponse.getId() > 0);

        User user = new UserRequests().read(userResponse.getId());

        assertEquals(user.getStatus(), Status.UNACCEPTED);

        String postBox = userResponse.getMail(); //запоминаю исходное занчение почты
        String pass = userResponse.getPassword(); // запоминаю первичное значение пароля


        //Step 2. Отправить запрос POST на создание пользователя повторно с тем же телом запроса
        response = client.execute(request);
        assertEquals(response.getStatusCode(), 422);
        Errors errorsResponse = response.getPayload(Errors.class);
        assertEquals(errorsResponse.getErrors().get(0), "Email уже существует");
        assertEquals(errorsResponse.getErrors().get(1), "Пользователь уже существует");


        //Step 3. Отправить запрос POST на создание пользователя повторно с тем же телом запроса,
        // при этом изменив "email" на невалидный, а "password" - на строку из 4 символов
        body.getUser().setMail("SDET.ru");
        body.getUser().setPassword("edcr");
        request = new RestAssuredRequest(RestMethod.POST, "/users.json", null, null, GsonProvider.GSON.toJson(body));
        response = client.execute(request);
        assertEquals(response.getStatusCode(), 422);
        Errors checkErrorThirdStep = response.getPayload(Errors.class);
        assertEquals(checkErrorThirdStep.getErrors().get(0), "Email имеет неверное значение");
        assertEquals(checkErrorThirdStep.getErrors().get(1), "Пользователь уже существует");
        assertEquals(checkErrorThirdStep.getErrors().get(2), "Пароль недостаточной длины (не может быть меньше 8 символа)");
        body.getUser().setMail(postBox);
        body.getUser().setPassword(pass);


        //Step 4. Отправить запрос PUT на изменение пользователя. Использовать данные из ответа запроса,
        // выполненного в шаге №1, но при этом изменить поле status = 1
        body.getUser().setStatus(1);
        request = new RestAssuredRequest(RestMethod.PUT, "/users/" + userResponse.getId() + ".json", null, null, GsonProvider.GSON.toJson(body));
        response = client.execute(request);
        assertEquals(response.getStatusCode(), 204);
        // User user2 = new UserRequests().read(userResponse.getId());
        assertEquals(new UserRequests().read(userResponse.getId()).getStatus(), Status.ACTIVE);

        //Step 5. Отправить запрос GET на получение пользователя
        request = new RestAssuredRequest(RestMethod.GET, "/users/" + userResponse.getId() + ".json", null, null, GsonProvider.GSON.toJson(body));
        response = client.execute(request);
        assertEquals(response.getStatusCode(), 200);

        UserDto userResponseStepFive = response.getPayload(UserInfoDto.class).getUser();
        assertEquals(new UserRequests().read(userResponseStepFive.getId()).getStatus(), Status.ACTIVE);

        //Step 6. Отправить запрос DELETE на удаление пользователя
        request = new RestAssuredRequest(RestMethod.DELETE, "/users/" + userResponse.getId() + ".json", null, null, GsonProvider.GSON.toJson(body));
        response = client.execute(request);
        assertEquals(response.getStatusCode(), 204);


        //Step 7. Отправить запрос DELETE на удаление пользователя (повторно)
        request = new RestAssuredRequest(RestMethod.DELETE, "/users/" + userResponse.getId() + ".json", null, null, GsonProvider.GSON.toJson(body));
        response = client.execute(request);
        assertEquals(response.getStatusCode(), 404);


    }

}
