package app.jg.og.zamong.dto.request;

import lombok.*;

import javax.validation.constraints.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpUserRequest {

    @NotBlank
    @Size(min = 1, max = 6)
    private String name;

    @NotBlank
    @Email
    private String email;

    @Size(min = 6, max = 6)
    private String authenticationCode;

    @NotBlank
    @Size(min = 6, max = 16)
    private String id;

    @NotBlank
    @Size(min = 8, max = 24)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$")
    private String password;
}
