package app.jg.og.zamong.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
public class CreateShareDreamResponse implements Response {

    private final UUID uuid;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;
}
