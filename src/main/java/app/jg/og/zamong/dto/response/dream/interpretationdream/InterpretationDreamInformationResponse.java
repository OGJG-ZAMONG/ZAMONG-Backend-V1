package app.jg.og.zamong.dto.response.dream.interpretationdream;

import app.jg.og.zamong.dto.response.Response;
import app.jg.og.zamong.entity.dream.enums.DreamType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
public class InterpretationDreamInformationResponse implements Response {

    private final UUID uuid;

    private final String title;

    private final String content;

    private final LocalDateTime updatedAt;

    private final List<DreamType> dreamTypes;

    private final String attachmentImage;

    private final User user;

    @Builder
    @Getter
    public static class User {

        private final UUID uuid;

        private final String id;

        private final String profile;
    }
}
