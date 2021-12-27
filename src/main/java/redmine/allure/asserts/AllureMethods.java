package redmine.allure.asserts;

import io.qameta.allure.Step;
import redmine.api.client.RestApiClient;
import redmine.api.client.RestRequest;
import redmine.api.dto.users.UserDto;
import redmine.api.dto.users.UserInfoDto;
import redmine.api.rest_assured.RestAssuredClient;
import redmine.api.rest_assured.RestAssuredRequest;

public class AllureMethods {

    @Step("Тело запроса")
    public static UserInfoDto requestDto(UserInfoDto dto){
        return dto;
    }

    @Step("Создание пользователя из ответа на запрос")
    public static UserDto userDtoFromResponse(UserDto dto) {
        return dto;
    }


    @Step("Формирование запроса")
    public static RestRequest shapingRequest(RestAssuredRequest request) {
        return request;
    }

    @Step("Создание API клиента")
    public static RestApiClient createApiClient(RestAssuredClient client){
        return client;
    }



}
