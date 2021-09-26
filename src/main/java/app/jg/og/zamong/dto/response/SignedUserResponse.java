package app.jg.og.zamong.dto.response;

import app.jg.og.zamong.entity.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class SignedUserResponse {

    private final UUID uuid;

    private final String name;

    private final String email;

    private final String id;

    public static SignedUserResponse of(User user) {
        return SignedUserResponse.builder()
                .uuid(user.getUuid())
                .name(user.getName())
                .email(user.getEmail())
                .id(user.getId())
                .build();
    }
}
