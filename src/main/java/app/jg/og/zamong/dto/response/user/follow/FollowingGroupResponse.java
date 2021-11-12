package app.jg.og.zamong.dto.response.user.follow;

import app.jg.og.zamong.dto.response.Response;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
public class FollowingGroupResponse implements Response {

    private final List<FollowingResponse> followings;

    private final Integer totalPage;

    private final Long totalSize;

    @Builder
    @Getter
    public static class FollowingResponse {

        private final UUID uuid;

        private final String id;

        private final String profile;

        @JsonProperty("follow_datetime")
        private final LocalDateTime followDateTime;

        private final Boolean isFollowing;
    }
}
