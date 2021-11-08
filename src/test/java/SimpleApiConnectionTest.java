import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;
import redmine.api.client.RestApiClient;
import redmine.api.client.RestMethod;
import redmine.api.dto.users.UserDto;
import redmine.api.dto.users.UserInfoDto;
import redmine.api.rest_assured.RestAssuredClient;
import redmine.api.rest_assured.RestAssuredRequest;
import redmine.model.user.Token;
import redmine.model.user.User;

import java.util.Collections;

import static io.restassured.RestAssured.given;

public class SimpleApiConnectionTest {
    private final RequestSpecification ADMIN_REQUEST_SPECIFICATION = given()
            .baseUri("http://172.26.25.188")
            .header("X-Redmine-API-Key", "55dfd83d5c925f999826c683114e589a4dd9f7e6")
            .log().all();


    @Test
    public void testSimpleRequest() {
        ADMIN_REQUEST_SPECIFICATION
                .request(Method.GET, "users.json")
                .then()
                .log().all()
                .statusCode(200);
    }

//    @Test
//    public void createUserTest() {
//        UserInfoDto body = new UserInfoDto(new UserDto("mixa", "kuznetsov", "mixa99", "mixa@kuznetsov.ru", "qazWSXedc")
//        );
//
//        ADMIN_REQUEST_SPECIFICATION
//                .contentType(ContentType.JSON)
//                .body(new Gson().toJson(body))
//                .request(Method.POST, "users.json")
//                .then()
//                .log().all()
//                .statusCode(201);
//        }

@Test
    public void testGetUser(){
        User user = new User();
        user.setIsAdmin(true);
        user.setTokens(Collections.singletonList(new Token(user)));

        user.create();

    RestApiClient apiClient = new RestAssuredClient(user);
    apiClient.execute(new RestAssuredRequest(
            RestMethod.GET, "/user.json",
            null,
            null,
            null
    ));

}
}
