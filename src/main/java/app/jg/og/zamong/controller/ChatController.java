package app.jg.og.zamong.controller;

import app.jg.og.zamong.dto.request.dream.selldream.SellDreamChatRequest;
import app.jg.og.zamong.dto.response.dream.selldream.chatting.ChatResponse;
import app.jg.og.zamong.service.dream.sell.chat.SellDreamChattingRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    private final SellDreamChattingRoomService sellDreamChattingRoomService;

    @MessageMapping("/chat.send")
    public void send(@Payload SellDreamChatRequest message) {
        ChatResponse response = sellDreamChattingRoomService.createChat(message);

        System.out.println(message.getRoom() + " 으로 메세지 전송: " + message.getChat());

        simpMessagingTemplate.convertAndSend("/topic/" + message.getRoom(), response);
        simpMessagingTemplate.convertAndSend("/topic/" + response.getTo(), response);
    }
}
