package app.jg.og.zamong.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class FollowUserResponse implements Response {

    private final UUID userId;
    private final UUID followerId;
}
