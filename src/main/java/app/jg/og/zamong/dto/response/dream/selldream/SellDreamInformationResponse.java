package app.jg.og.zamong.dto.response.dream.selldream;

import app.jg.og.zamong.dto.response.Response;
import app.jg.og.zamong.entity.dream.enums.DreamType;
import app.jg.og.zamong.entity.dream.enums.SalesStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
public class SellDreamInformationResponse implements Response {

    private final UUID uuid;

    private final String title;

    private final String content;

    private final LocalDateTime updatedAt;

    private final List<DreamType> dreamTypes;

    private final String attachmentImage;

    private final Integer cost;

    private final SalesStatus status;

    private final User user;

    @Builder
    @Getter
    public static class User {

        private final UUID uuid;

        private final String id;

        private final String profile;
    }
}
