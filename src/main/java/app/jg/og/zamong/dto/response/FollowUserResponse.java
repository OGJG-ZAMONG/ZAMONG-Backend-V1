package app.jg.og.zamong.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
public class FollowUserResponse implements Response {

    private final UUID userId;

    private final UUID followerId;

    @JsonProperty("follow_datetime")
    private final LocalDateTime followDateTime;
}
