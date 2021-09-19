package app.jg.og.zamong.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.*;

@Builder
@Getter
public class SignUpUserRequest {

    @NotBlank
    @Size(min = 1, max = 6)
    private final String name;

    @NotBlank
    @Email
    private final String email;

    @JsonProperty("authentication_code")
    @Size(min = 6, max = 6)
    private final String authenticationCode;

    @NotBlank
    @Size(min = 6, max = 16)
    private final String id;

    @NotBlank
    @Size(min = 8, max = 24)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$")
    private final String password;
}
