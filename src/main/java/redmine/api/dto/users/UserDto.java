package redmine.api.dto.users;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@AllArgsConstructor
public class UserDto {
    private String login;
    private Boolean admmin;

    @SerializedName("firstname")
    private String firstname;

    @SerializedName("lastname")
    private String lastName;

    private String mail;
    private String password;

    @SerializedName("created_on")
    private LocalDateTime createdOn;

    @SerializedName("last_login_on")
    private LocalDateTime lastLoginOn;


}
