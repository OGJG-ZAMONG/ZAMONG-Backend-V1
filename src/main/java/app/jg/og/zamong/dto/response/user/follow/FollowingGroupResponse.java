package app.jg.og.zamong.dto.response.user.follow;

import app.jg.og.zamong.dto.response.Response;
import app.jg.og.zamong.dto.response.user.UserResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class FollowingGroupResponse implements Response {

    private final List<UserResponse> followings;

    private final Integer totalPage;

    private final Long totalSize;
}
