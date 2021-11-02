package app.jg.og.zamong.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class DoSellRequestDreamResponse implements Response {

    private final UUID userUuid;

    private final UUID sellDreamUuid;
}
