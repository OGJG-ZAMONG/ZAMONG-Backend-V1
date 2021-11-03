package app.jg.og.zamong.dto.response.dream.sharedream;

import app.jg.og.zamong.dto.response.Response;
import app.jg.og.zamong.entity.dream.enums.DreamQuality;
import app.jg.og.zamong.entity.dream.enums.DreamType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
public class ShareDreamInformationResponse implements Response {

    private final UUID uuid;

    private final String title;

    private final String content;

    private LocalDateTime updatedAt;

    private List<DreamType> dreamTypes;

    private String attachmentImage;

    private DreamQuality quality;

    private Boolean isShared;

    private LocalDateTime sleepBeginDateTime;

    private LocalDateTime sleepEndDateTime;

    private LocalDateTime shareDateTime;

    private final Integer lucyCount;

    private final User user;

    @Builder
    @Getter
    public static class User {

        private final UUID uuid;

        private final String id;

        private final String profile;
    }
}
