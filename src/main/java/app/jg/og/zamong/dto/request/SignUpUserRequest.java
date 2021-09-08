package app.jg.og.zamong.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SignUpUserRequest {

    private final String name;

    private final String email;

    @JsonProperty("authentication_code")
    private final String authenticationCode;

    private final String id;

    private final String password;
}
