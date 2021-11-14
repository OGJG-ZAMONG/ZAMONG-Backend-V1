package app.jg.og.zamong.dto.response.dream;

import app.jg.og.zamong.dto.response.Response;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
public class CreateDreamResponse implements Response {

    private final UUID uuid;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;
}
