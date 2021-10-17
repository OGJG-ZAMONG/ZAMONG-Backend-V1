package app.jg.og.zamong.dto.response;

import app.jg.og.zamong.entity.dream.enums.DreamType;
import app.jg.og.zamong.entity.dream.sharedream.ShareDream;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Builder
@Getter
public class ShareDreamResponse {

    private final UUID uuid;

    private final String defaultPostingImage;

    private final String profile;

    private final String title;

    private final Integer lucyCount;

    private final List<DreamType> dreamTypes;

    @JsonProperty("share_datetime")
    private final LocalDateTime shareDateTime;

    public static ShareDreamResponse of(ShareDream shareDream) {
        return ShareDreamResponse.builder()
                .uuid(shareDream.getUuid())
                .title(shareDream.getTitle())
                .profile(shareDream.getUser().getProfile())
                .lucyCount(shareDream.getLucyCount())
                .dreamTypes(shareDream.getDreamTypes()
                        .stream()
                        .map(dt -> dt.toCode())
                        .collect(Collectors.toList()))
                .defaultPostingImage(shareDream.getDefaultImage())
                .shareDateTime(shareDream.getShareDateTime())
                .build();
    }
}
