package app.jg.og.zamong.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class CreateShareDreamResponse implements Response {

    private final UUID uuid;

    private final String title;
}
