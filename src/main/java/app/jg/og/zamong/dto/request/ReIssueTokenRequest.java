package app.jg.og.zamong.dto.request;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReIssueTokenRequest {

    private String refreshToken;
}
