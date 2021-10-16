package app.jg.og.zamong.service.user.follow;

import app.jg.og.zamong.dto.response.FollowUserResponse;
import app.jg.og.zamong.dto.response.FollowerGroupResponse;
import app.jg.og.zamong.dto.response.FollowingGroupResponse;

public interface UserFollowService {

    FollowUserResponse followUser(String uuid, String followerUuid);
    FollowingGroupResponse queryFollowings(String uuid, int page, int size);
    FollowerGroupResponse queryFollowers(String uuid, int page, int size);
}
