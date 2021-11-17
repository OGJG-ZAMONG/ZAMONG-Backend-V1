package app.jg.og.zamong.dto.response.dream.selldream.chatting;

import app.jg.og.zamong.dto.response.Response;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ChattingRoomGroupResponse implements Response {

    private final List<ChattingRoomResponse> rooms;

    private final Integer count;
}
