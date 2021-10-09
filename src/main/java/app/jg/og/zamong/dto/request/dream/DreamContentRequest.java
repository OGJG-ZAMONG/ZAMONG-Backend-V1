package app.jg.og.zamong.dto.request.dream;

import lombok.*;

import javax.validation.constraints.NotNull;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DreamContentRequest {

    @NotNull(message = "내용을 반드시 입력해야 합니다")
    private String content;
}
