package app.jg.og.zamong.dto.response;

import app.jg.og.zamong.entity.dream.sharedream.ShareDream;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
public class ShareDreamResponse {

    private final UUID uuid;

    private final String defaultPostingImage;

    private final String profile;

    private final String title;

    private final LocalDateTime createdAt;

    private final Boolean isShared;
}
