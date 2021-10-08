package app.jg.og.zamong.dto.request.dream.sharedream;

import app.jg.og.zamong.entity.dream.enums.DreamQuality;
import lombok.*;

import javax.validation.constraints.NotNull;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShareDreamQualityRequest {

    @NotNull(message = "꿈의 품질을 입력해주세요")
    private DreamQuality quality;
}
