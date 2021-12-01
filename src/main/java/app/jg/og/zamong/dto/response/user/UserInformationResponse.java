package app.jg.og.zamong.dto.response.user;

import app.jg.og.zamong.dto.response.Response;
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

    private final String profile;

    private Integer shareDreamCount;

    private Integer boughtSellDreamCount;

    private Integer lucyCount;

    public static UserInformationResponse of(User user, Integer shareDreamCount, Integer boughtSellDreamCount) {
        return UserInformationResponse.builder()
                .uuid(user.getUuid())
                .name(user.getName())
                .email(user.getEmail())
                .id(user.getId())
                .profile(user.getProfile())
                .shareDreamCount(shareDreamCount)
                .boughtSellDreamCount(boughtSellDreamCount)
                .lucyCount(user.getLucyCount())
                .build();
    }
}
