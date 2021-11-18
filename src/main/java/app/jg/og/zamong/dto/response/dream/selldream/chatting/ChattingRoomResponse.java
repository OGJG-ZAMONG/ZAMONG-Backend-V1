package app.jg.og.zamong.dto.response.dream.selldream.chatting;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class ChattingRoomResponse {

    private final UUID uuid;

    private final String title;

    private final ChatResponse lastChat;
}
