package app.jg.og.zamong.entity.dream.selldream.chatting.room.querydsl;

import app.jg.og.zamong.entity.dream.selldream.chatting.room.SellDreamChattingRoom;
import app.jg.og.zamong.entity.user.User;

import java.util.List;

public interface SellDreamChattingRoomQueryDslRepository {

    List<SellDreamChattingRoom> findByUserOrderByLastChatDesc(User user);
}
