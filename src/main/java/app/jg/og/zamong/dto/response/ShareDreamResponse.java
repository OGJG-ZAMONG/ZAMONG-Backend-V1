package app.jg.og.zamong.dto.response;

import app.jg.og.zamong.entity.dream.enums.DreamType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
public class ShareDreamResponse {

    private final UUID uuid;

    private final String defaultPostingImage;

    private final String profile;

    private final String title;

    private final Boolean isShared;

    private final Integer lucyCount;

    private final List<DreamType> dreamTypes;

    @JsonProperty("share_datetime")
    private final LocalDateTime shareDateTime;
}
