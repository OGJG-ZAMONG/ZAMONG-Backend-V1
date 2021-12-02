package app.jg.og.zamong.dto.request.dream;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LeaveUserRequest {

    private String password;
}
