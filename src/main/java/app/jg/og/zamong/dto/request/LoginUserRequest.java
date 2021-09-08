package app.jg.og.zamong.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginUserRequest {

    @JsonProperty("user_identity")
    private final String userIdentity;

    private final String password;
}
