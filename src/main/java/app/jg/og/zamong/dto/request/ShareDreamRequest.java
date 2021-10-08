package app.jg.og.zamong.dto.request;

import app.jg.og.zamong.entity.dream.enums.DreamQuality;
import app.jg.og.zamong.entity.dream.enums.DreamType;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShareDreamRequest {

    @NotNull(message = "제목을 반드시 입력해야 합니다")
    @Size(min = 1, max = 100, message = "제목은 100자 이하여야 합니다")
    public String title;

    @NotNull(message = "내용을 반드시 입력해야 합니다")
    private String content;

    @NotNull(message = "꿈의 유형을 입력해주세요")
    private List<DreamType> dreamTypes;

    @NotNull(message = "꿈의 품질을 입력해주세요")
    private DreamQuality quality;

    @NotNull(message = "수면시작시각을 입력해주세요")
    private LocalDateTime sleepBeginDatetime;

    @NotNull(message = "수면종료시각을 입력해주세요")
    private LocalDateTime sleepEndDatetime;
}
