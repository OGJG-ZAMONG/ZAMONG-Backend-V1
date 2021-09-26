package app.jg.og.zamong.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginUserRequest {

    @JsonProperty("user_identity")
    @NotBlank
    @Size(min = 6, max = 320)
    private String userIdentity;

    @NotBlank
    @Size(min = 8, max = 24)
    private String password;
}
