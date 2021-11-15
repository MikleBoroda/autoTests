import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.api.client.RestApiClient;
import redmine.api.client.RestRequest;
import redmine.api.client.RestResponse;
import redmine.api.dto.users.UserDto;
import redmine.api.dto.users.UsersListDto;
import redmine.api.rest_assured.RestAssuredClient;
import redmine.api.rest_assured.RestAssuredRequest;
import redmine.model.user.Email;
import redmine.model.user.Status;
import redmine.model.user.Token;
import redmine.model.user.User;

import java.util.*;

import static java.util.Collections.singletonList;
import static redmine.api.client.RestMethod.*;

public class GetLockedUsersListTest {
    private RestApiClient client;
    private RestRequest request;
    private User lockedUser;

    @BeforeMethod
    public void prepareFixtures() {
        User user = new User() {{
            setIsAdmin(true);
            setTokens(singletonList(new Token(this)));
        }}.create();

        lockedUser = new User() {{
            setStatus(Status.LOKED);
            setEmails(singletonList(new Email(this)));
        }}.create();

        client = new RestAssuredClient(user);

        Map<String, String> lockedUserParams = new HashMap<>();
        lockedUserParams.put("status", "3");

        request = new RestAssuredRequest(GET, "/users.json", null, lockedUserParams, null);
    }

    @Test
    public void getLockedUsersListTest() {

        RestResponse response = client.execute(request);

        Assert.assertEquals(response.getStatusCode(), 200);

        UsersListDto responseData = response.getPayload(UsersListDto.class);

        UserDto lockedUserFromApi = responseData.getUsers().stream().filter(
                userDto -> userDto.getLogin().equals(lockedUser.getLogin())
        ).findFirst().get();

        Assert.assertEquals(lockedUserFromApi.getMail(), lockedUser.getEmails().get(0).getAddress());
        Assert.assertEquals(lockedUserFromApi.getFirstName(),lockedUser.getFirstName());
        Assert.assertEquals(lockedUserFromApi.getLastName(), lockedUser.getLastName());

    }
}
