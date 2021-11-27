package app.jg.og.zamong.entity.dream.selldream.chatting.room;

import app.jg.og.zamong.entity.dream.selldream.chatting.room.querydsl.SellDreamChattingRoomQueryDslRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SellDreamChattingRoomRepository extends CrudRepository<SellDreamChattingRoom, UUID>, SellDreamChattingRoomQueryDslRepository {
}
