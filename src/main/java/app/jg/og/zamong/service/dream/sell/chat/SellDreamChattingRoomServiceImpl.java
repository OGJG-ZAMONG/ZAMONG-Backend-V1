package app.jg.og.zamong.service.dream.sell.chat;

import app.jg.og.zamong.dto.response.dream.selldream.chatting.ChatResponse;
import app.jg.og.zamong.dto.response.dream.selldream.chatting.ChattingRoomGroupResponse;
import app.jg.og.zamong.dto.response.dream.selldream.chatting.ChattingRoomResponse;
import app.jg.og.zamong.entity.dream.selldream.chatting.room.SellDreamChattingRoom;
import app.jg.og.zamong.entity.dream.selldream.chatting.room.SellDreamChattingRoomRepository;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.service.securitycontext.SecurityContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SellDreamChattingRoomServiceImpl implements SellDreamChattingRoomService {

    private final SellDreamChattingRoomRepository sellDreamChattingRoomRepository;

    private final SecurityContextService securityContextService;

    @Override
    public ChattingRoomGroupResponse queryChattingRoom() {
        User user = securityContextService.getPrincipal().getUser();

        List<SellDreamChattingRoom> rooms = sellDreamChattingRoomRepository.findBySeller(user);

        List<ChattingRoomResponse> responses = rooms.stream().map(this::of).collect(Collectors.toList());

        return ChattingRoomGroupResponse.builder()
                .rooms(responses)
                .count(responses.size())
                .build();
    }

    private ChattingRoomResponse of(SellDreamChattingRoom sellDreamChattingRoom) {
        ChatResponse lastChat = sellDreamChattingRoom.getLastChat() == null ? null  : ChatResponse.builder()
                .uuid(sellDreamChattingRoom.getLastChat().getUuid())
                .user(ChatResponse.User.builder()
                        .uuid(sellDreamChattingRoom.getLastChat().getUser().getUuid())
                        .id(sellDreamChattingRoom.getLastChat().getUser().getId())
                        .profile(sellDreamChattingRoom.getLastChat().getUser().getProfile())
                        .build())
                .chat(sellDreamChattingRoom.getLastChat().getChat())
                .createdAt(sellDreamChattingRoom.getLastChat().getCreatedAt())
                .build();

        return ChattingRoomResponse.builder()
                .uuid(sellDreamChattingRoom.getUuid())
                .title(sellDreamChattingRoom.getSellDream().getTitle())
                .lastChat(lastChat)
                .build();
    }
}
