package app.jg.og.zamong.entity.dream.selldream.chatting.room;

import app.jg.og.zamong.entity.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface SellDreamChattingRoomRepository extends CrudRepository<SellDreamChattingRoom, UUID> {

    List<SellDreamChattingRoom> findBySeller(User seller);
}
