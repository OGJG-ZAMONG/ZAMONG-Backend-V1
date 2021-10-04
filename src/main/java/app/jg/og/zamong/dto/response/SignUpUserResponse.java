package app.jg.og.zamong.dto.response;

import app.jg.og.zamong.entity.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class SignUpUserResponse implements Response {

    private final UUID uuid;

    private final String name;

    private final String email;

    private final String id;

    public static SignUpUserResponse of(User user) {
        return SignUpUserResponse.builder()
                .uuid(user.getUuid())
                .name(user.getName())
                .email(user.getEmail())
                .id(user.getId())
                .build();
    }
}
