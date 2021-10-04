package app.jg.og.zamong.dto.request;

import lombok.*;

import javax.validation.constraints.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpUserRequest {

    @NotNull(message = "이름을 반드시 입력해야 합니다")
    @Size(min = 1, max = 6, message = "이름은 1자 이상 6자 이하여야 합니다")
    private String name;

    @Email(message = "이메일 형식이 올바르지 않습니다")
    @NotNull(message = "이메일을 반드시 입력해야 합니다")
    private String email;

    @NotNull(message = "인증코드를 반드시 입력해야 합니다")
    @Size(min = 6, max = 6, message = "인증코드는 형식이 올바르지 않습니다")
    private String authenticationCode;

    @NotNull(message = "아이디를 반드시 입력해야 합니다")
    @Size(min = 6, max = 16, message = "아이디는 6자 이상 16자 이하여야 합니다")
    private String id;

    @NotNull(message = "비밀번호를 반드시 입력해야 합니다")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$", message = "비밀번호는 영문 대소문자와 숫자 특수기호 포함 8자 이상이어야 합니다")
    @Size(max = 24, message = "너무 긴 비밀번호입니다")
    private String password;
}
