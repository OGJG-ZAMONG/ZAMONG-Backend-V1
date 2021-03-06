package app.jg.og.zamong.dto.request.dream;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DreamCommentRequest {

    @NotNull(message = "내용을 반드시 입력해야 합니다")
    @NotBlank(message = "내용을 반드시 입력해야 합니다")
    @Size(max = 100, message = "100자 이상 입력할 수 없습니다")
    private String content;

    @JsonProperty("p_comment")
    private String pComment;

    private Boolean isAnonymous;
}
