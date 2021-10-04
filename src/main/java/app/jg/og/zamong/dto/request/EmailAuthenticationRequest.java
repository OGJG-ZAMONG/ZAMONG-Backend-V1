package app.jg.og.zamong.dto.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailAuthenticationRequest {

    @Email(message = "이메일 형식이 올바르지 않습니다")
    @NotNull(message = "이메일을 반드시 입력해야 합니다")
    private String address;
}