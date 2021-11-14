package app.jg.og.zamong.dto.response.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
public class UserResponse {

    private final UUID uuid;

    private final String id;

    private final String profile;

    @JsonProperty("follow_datetime")
    private final LocalDateTime followDateTime;

    private final Boolean isFollowing;
}
