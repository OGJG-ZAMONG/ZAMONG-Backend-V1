package app.jg.og.zamong.dto.response;

import app.jg.og.zamong.entity.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class UserInformationResponse implements Response {

    private final UUID uuid;

    private final String name;

    private final String email;

    private final String id;

    private Integer shareDreamCount;

    private Integer lucyCount;
}
