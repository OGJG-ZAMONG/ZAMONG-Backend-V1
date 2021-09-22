package app.jg.og.zamong.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Builder
public class LoginUserRequest {

    @JsonProperty("user_identity")
    @NotBlank
    @Size(min = 6, max = 320)
    private final String userIdentity;

    @NotBlank
    @Size(min = 8, max = 24)
    private final String password;
}
