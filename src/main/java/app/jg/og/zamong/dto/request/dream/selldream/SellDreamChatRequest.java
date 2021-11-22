package app.jg.og.zamong.dto.request.dream.selldream;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SellDreamChatRequest {

    private final String chat;

    private final String room;

    private final String from;
}
