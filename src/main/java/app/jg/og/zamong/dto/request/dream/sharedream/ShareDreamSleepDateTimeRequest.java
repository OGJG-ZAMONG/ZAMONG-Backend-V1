package app.jg.og.zamong.dto.request.dream.sharedream;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShareDreamSleepDateTimeRequest {

    @NotNull(message = "수면시작시각을 입력해주세요")
    @JsonProperty("sleep_begin_datetime")
    private LocalDateTime sleepBeginDateTime;

    @NotNull(message = "수면종료시각을 입력해주세요")
    @JsonProperty("sleep_end_datetime")
    private LocalDateTime sleepEndDateTime;
}
