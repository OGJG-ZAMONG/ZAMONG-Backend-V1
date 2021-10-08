package app.jg.og.zamong.dto.request.dream;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DreamTitleRequest {

    @NotNull(message = "제목을 반드시 입력해야 합니다")
    @Size(min = 1, max = 100, message = "제목은 100자 이하여야 합니다")
    private String title;
}
