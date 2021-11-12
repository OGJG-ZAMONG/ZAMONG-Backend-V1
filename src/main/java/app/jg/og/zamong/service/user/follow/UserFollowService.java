package app.jg.og.zamong.service.user.follow;

import app.jg.og.zamong.dto.response.user.follow.FollowUserResponse;
import app.jg.og.zamong.dto.response.user.follow.FollowerGroupResponse;
import app.jg.og.zamong.dto.response.user.follow.FollowingGroupResponse;

public interface UserFollowService {

    FollowUserResponse followUser(String uuid, String followerUuid);
    FollowingGroupResponse queryFollowings(String uuid, int page, int size);
    FollowerGroupResponse queryFollowers(String uuid, int page, int size);
}
