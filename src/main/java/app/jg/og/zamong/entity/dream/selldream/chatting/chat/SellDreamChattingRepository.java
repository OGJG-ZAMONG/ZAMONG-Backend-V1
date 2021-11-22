package app.jg.og.zamong.entity.dream.selldream.chatting.chat;

import app.jg.og.zamong.entity.dream.selldream.chatting.room.SellDreamChattingRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface SellDreamChattingRepository extends PagingAndSortingRepository<SellDreamChatting, UUID> {

    Page<SellDreamChatting> findByRoom(SellDreamChattingRoom room, Pageable pageable);
}
