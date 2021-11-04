package app.jg.og.zamong.dto.response.dream.sharedream;

import app.jg.og.zamong.dto.response.Response;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
public class ShareDreamTimeTableResponseV2 implements Response {

    private final List<ShareDreamResponse> timetables;

    @Builder
    @Getter
    public static class ShareDreamResponse {

        private final LocalDate date;

        private final UUID uuid;

        private final String defaultPostingImage;

        private final String profile;

        private final String title;

        private final LocalDateTime createdAt;

        private final Boolean isShared;
    }
}
