package app.jg.og.zamong.dto.response.user;

import app.jg.og.zamong.dto.response.Response;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class UserGroupResponse implements Response {

    private final List<UserResponse> users;
}
