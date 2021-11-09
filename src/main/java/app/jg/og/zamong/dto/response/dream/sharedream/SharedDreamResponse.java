package app.jg.og.zamong.dto.response.dream.sharedream;

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
public class SharedDreamResponse {

    private final UUID uuid;

    private final String defaultPostingImage;

    private final User user;

    private final String title;

    private final Integer lucyCount;

    private final List<DreamType> dreamTypes;

    @JsonProperty("share_datetime")
    private final LocalDateTime shareDateTime;

    @Builder
    @Getter
    public static class User {

        private final UUID uuid;

        private final String id;

        private final String profile;
    }

    public static SharedDreamResponse of(ShareDream shareDream) {
        return SharedDreamResponse.builder()
                .uuid(shareDream.getUuid())
                .title(shareDream.getTitle())
                .user(User.builder()
                        .uuid(shareDream.getUser().getUuid())
                        .profile(shareDream.getUser().getProfile())
                        .id(shareDream.getUser().getId())
                        .build())
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
