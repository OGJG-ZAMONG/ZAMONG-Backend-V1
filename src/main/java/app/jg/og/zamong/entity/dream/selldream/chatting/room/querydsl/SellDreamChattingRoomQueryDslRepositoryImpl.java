package app.jg.og.zamong.entity.dream.selldream.chatting.room.querydsl;

import app.jg.og.zamong.entity.dream.selldream.chatting.room.QSellDreamChattingRoom;
import app.jg.og.zamong.entity.dream.selldream.chatting.room.SellDreamChattingRoom;
import app.jg.og.zamong.entity.user.User;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class SellDreamChattingRoomQueryDslRepositoryImpl extends QuerydslRepositorySupport implements SellDreamChattingRoomQueryDslRepository {

    public SellDreamChattingRoomQueryDslRepositoryImpl() {
        super(SellDreamChattingRoom.class);
    }

    @Override
    public List<SellDreamChattingRoom> findByUserOrderByLastChatDesc(User user) {
        QSellDreamChattingRoom sellDreamChattingRoom = QSellDreamChattingRoom.sellDreamChattingRoom;

        return from(sellDreamChattingRoom)
                .where(sellDreamChattingRoom.customer.eq(user)
                        .or(sellDreamChattingRoom.seller.eq(user)))
                .innerJoin(sellDreamChattingRoom.lastChat)
                .orderBy(sellDreamChattingRoom.lastChat.createdAt.desc())
                .fetch();
    }
}
