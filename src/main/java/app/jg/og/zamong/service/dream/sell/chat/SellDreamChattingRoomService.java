package app.jg.og.zamong.service.dream.sell.chat;

import app.jg.og.zamong.dto.request.dream.selldream.SellDreamChatRequest;
import app.jg.og.zamong.dto.response.dream.selldream.chatting.ChatGroupResponse;
import app.jg.og.zamong.dto.response.dream.selldream.chatting.ChatResponse;
import app.jg.og.zamong.dto.response.dream.selldream.chatting.ChattingRoomGroupResponse;

public interface SellDreamChattingRoomService {

    ChattingRoomGroupResponse queryChattingRoom();

    ChatResponse createChat(SellDreamChatRequest request);
    ChatGroupResponse queryChats(String uuid, int page, int size);
}
