package app.jg.og.zamong.dto.response.dream.comment;

import app.jg.og.zamong.dto.response.Response;
import app.jg.og.zamong.dto.response.user.UserInformationResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class DreamCommentResponse implements Response {

    private final UUID uuid;

    private final String content;

    private final UserInformationResponse user;

    private final Integer depth;
}
