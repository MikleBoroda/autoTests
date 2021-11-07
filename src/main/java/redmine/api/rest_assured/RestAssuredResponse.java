package redmine.api.rest_assured;

import com.google.gson.Gson;
import io.restassured.http.Header;
import io.restassured.response.Response;
import lombok.Getter;
import redmine.api.client.RestResponse;

import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class RestAssuredResponse implements RestResponse {
    private int statusCode;
    private Map<String, String> headers;
    private String payload;

    public RestAssuredResponse(Response response) {
        this.statusCode = response.getStatusCode();
        this.headers = response.getHeaders().asList().stream()
                .collect(Collectors.toMap(Header::getName, Header::getValue));
        this.payload = response.getBody().asString();
    }

    @Override
    public <T> T getPayload(Class<T> clazz) {
        return new Gson().fromJson(payload, clazz);
    }
}
