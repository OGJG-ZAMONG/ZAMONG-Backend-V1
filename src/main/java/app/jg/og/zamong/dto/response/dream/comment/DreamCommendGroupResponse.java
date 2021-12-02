package app.jg.og.zamong.dto.response.dream.comment;

import app.jg.og.zamong.dto.response.Response;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
public class DreamCommendGroupResponse implements Response {

    private final List<CommentResponse> comments;

    @Builder
    @Getter
    public static class CommentResponse {

        private final UUID uuid;

        private final Boolean isChecked;

        private final LocalDateTime dateTime;

        private final UUID userUuid;

        private final String userProfile;

        private final String userId;

        private final String content;

        private final Integer likeCount;

        private final Integer dislikeCount;

        private final Boolean isLike;

        private final Boolean isDisLike;

        private final Boolean itsMe;
    }
}
