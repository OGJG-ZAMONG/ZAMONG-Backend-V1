package app.jg.og.zamong.service.user;

import app.jg.og.zamong.dto.response.FollowUserResponse;
import app.jg.og.zamong.dto.response.UserInformationResponse;

public interface UserService {

    UserInformationResponse queryUserInformation(String uuid);
    FollowUserResponse followUser(String uuid, String followerUuid);
}
