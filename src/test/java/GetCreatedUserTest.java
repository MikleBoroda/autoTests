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
import redmine.model.user.Status;
import redmine.model.user.Token;
import redmine.model.user.User;

import java.util.Collections;

import static org.testng.Assert.*;

public class GetCreatedUserTest {
    private User createdUser;
    private RestApiClient client;
    private RestRequest request;

    @BeforeMethod
    public void prepareFixtures() {

        createdUser = new User() {{
            setStatus(Status.UNACCEPTED);
        }}.create();

        User apiUser = new User() {{
            setTokens(Collections.singletonList(new Token(this)));
            setIsAdmin(true);
        }}.create();

        client = new RestAssuredClient(apiUser);

        Integer createdUserId = createdUser.getId();

        request = new RestAssuredRequest(RestMethod.GET, "/users/" + createdUserId + ".json", null, null, null);

    }

    @Test
    public void getCreatedUserTest() {
        RestResponse response = client.execute(request);

        assertEquals(response.getStatusCode(), 200);

        UserInfoDto responseData = response.getPayload(UserInfoDto.class);
        UserDto responseUser = responseData.getUser();

        assertEquals(responseUser.getLastName(), createdUser.getLastName());
        assertEquals(responseUser.getFirstName(), createdUser.getFirstName());
        assertEquals(responseUser.getAdmin(), createdUser.getIsAdmin());
        assertNull(responseUser.getMail());
        assertEquals(responseUser.getStatus().intValue(), createdUser.getStatus().statusCode);
    }

}
