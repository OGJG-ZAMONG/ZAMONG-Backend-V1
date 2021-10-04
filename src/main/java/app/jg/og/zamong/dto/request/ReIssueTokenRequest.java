package app.jg.og.zamong.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReIssueTokenRequest {

    @NotNull
    @Size(min = 6, max = 500)
    private String refreshToken;
}
