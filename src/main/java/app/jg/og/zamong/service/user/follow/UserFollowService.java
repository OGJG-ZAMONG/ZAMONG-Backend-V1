package app.jg.og.zamong.service.user.follow;

import app.jg.og.zamong.dto.response.FollowUserResponse;

public interface UserFollowService {

    FollowUserResponse followUser(String uuid, String followerUuid);
}
