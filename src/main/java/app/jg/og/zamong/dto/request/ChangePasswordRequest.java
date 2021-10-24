package app.jg.og.zamong.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangePasswordRequest {

    @NotNull(message = "비밀번호를 반드시 입력해야 합니다")
    @Size(max = 24, message = "너무 긴 비밀번호입니다")
    private String oldPassword;

    @NotNull(message = "비밀번호를 반드시 입력해야 합니다")
    @Size(max = 24, message = "너무 긴 비밀번호입니다")
    private String newPassword;
}
