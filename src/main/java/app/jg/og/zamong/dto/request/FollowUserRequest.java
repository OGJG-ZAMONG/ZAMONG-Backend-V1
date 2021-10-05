package app.jg.og.zamong.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowUserRequest {

    @NotNull(message = "유저 아이디를 반드시 입력해야 합니다")
    private String userUuid;
}
