package app.jg.og.zamong.dto.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResetPasswordRequest {

    @NotNull(message = "비밀번호를 반드시 입력해야 합니다")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$", message = "비밀번호는 영문 대소문자와 숫자 특수기호 포함 8자 이상이어야 합니다")
    @Size(max = 24, message = "너무 긴 비밀번호입니다")
    private String newPassword;

    @NotNull
    private String changePasswordToken;

    @Email(message = "이메일 형식이 올바르지 않습니다")
    @NotNull(message = "이메일을 반드시 입력해야 합니다")
    private String email;
}
