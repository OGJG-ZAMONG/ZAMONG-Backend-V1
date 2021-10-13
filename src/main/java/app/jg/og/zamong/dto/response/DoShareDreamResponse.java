package app.jg.og.zamong.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
public class DoShareDreamResponse implements Response {

    private final UUID uuid;

    @JsonProperty("share_datetime")
    private final LocalDateTime shareDateTime;
}
