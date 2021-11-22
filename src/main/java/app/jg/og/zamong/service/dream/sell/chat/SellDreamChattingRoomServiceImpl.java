package app.jg.og.zamong.service.dream.sell.chat;

import app.jg.og.zamong.dto.request.dream.selldream.SellDreamChatRequest;
import app.jg.og.zamong.dto.response.dream.selldream.chatting.ChatGroupResponse;
import app.jg.og.zamong.dto.response.dream.selldream.chatting.ChatResponse;
import app.jg.og.zamong.dto.response.dream.selldream.chatting.ChattingRoomGroupResponse;
import app.jg.og.zamong.dto.response.dream.selldream.chatting.ChattingRoomResponse;
import app.jg.og.zamong.entity.dream.selldream.chatting.chat.SellDreamChatting;
import app.jg.og.zamong.entity.dream.selldream.chatting.chat.SellDreamChattingRepository;
import app.jg.og.zamong.entity.dream.selldream.chatting.room.SellDreamChattingRoom;
import app.jg.og.zamong.entity.dream.selldream.chatting.room.SellDreamChattingRoomRepository;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.entity.user.UserRepository;
import app.jg.og.zamong.exception.business.DreamNotFoundException;
import app.jg.og.zamong.exception.business.UserNotFoundException;
import app.jg.og.zamong.service.securitycontext.SecurityContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SellDreamChattingRoomServiceImpl implements SellDreamChattingRoomService {

    private final SellDreamChattingRoomRepository sellDreamChattingRoomRepository;
    private final UserRepository userRepository;
    private final SellDreamChattingRepository sellDreamChattingRepository;

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

    @Override
    public ChatResponse createChat(SellDreamChatRequest request) {
        SellDreamChattingRoom room = sellDreamChattingRoomRepository.findById(UUID.fromString(request.getRoom()))
                .orElseThrow(() -> new DreamNotFoundException("Room Not Found"));

        User user = userRepository.findById(UUID.fromString(request.getFrom()))
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));

        SellDreamChatting chat = sellDreamChattingRepository.save(SellDreamChatting.builder()
                .chat(request.getChat())
                .user(user)
                .room(room)
                .build());

        return ChatResponse.builder()
                .uuid(chat.getUuid())
                .user(ChatResponse.User.builder()
                        .uuid(user.getUuid())
                        .id(user.getId())
                        .profile(user.getProfile())
                        .build())
                .chat(chat.getChat())
                .createdAt(chat.getCreatedAt())
                .itsMe(true)
                .build();
    }

    @Override
    public ChatGroupResponse queryChats(String uuid) {
        User user = securityContextService.getPrincipal().getUser();

        SellDreamChattingRoom room = sellDreamChattingRoomRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new DreamNotFoundException("Room Not Found"));

        return ChatGroupResponse.builder()
                .chats(room.getChats().stream()
                        .map(chat -> ChatResponse.builder()
                                .uuid(chat.getUuid())
                                .user(ChatResponse.User.builder()
                                        .uuid(chat.getUser().getUuid())
                                        .id(chat.getUser().getId())
                                        .profile(chat.getUser().getProfile())
                                        .build())
                                .chat(chat.getChat())
                                .createdAt(chat.getCreatedAt())
                                .itsMe(chat.getUser().equals(user))
                                .build())
                        .collect(Collectors.toList()))
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
