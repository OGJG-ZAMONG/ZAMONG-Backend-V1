package app.jg.og.zamong.dto.request.dream.sharedream;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShareDreamSleepDateTimeRequest {

    @NotNull(message = "수면시작시각을 입력해주세요")
    private LocalDateTime sleepBeginDatetime;

    @NotNull(message = "수면종료시각을 입력해주세요")
    private LocalDateTime sleepEndDatetime;
}
