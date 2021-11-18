package app.jg.og.zamong.dto.response.dream.selldream.chatting;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
public class ChatResponse {

    private final UUID uuid;

    private final User user;

    private final String chat;

    private final LocalDateTime createdAt;

    @Builder
    @Getter
    public static class User {

        private final UUID uuid;

        private final String id;

        private final String profile;
    }
}
