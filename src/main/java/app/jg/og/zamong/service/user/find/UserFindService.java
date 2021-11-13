package app.jg.og.zamong.service.user.find;

import app.jg.og.zamong.dto.response.user.UserGroupResponse;
import app.jg.og.zamong.dto.response.user.UserInformationResponse;

public interface UserFindService {

    UserInformationResponse queryUserInformation(String uuid);
    UserInformationResponse queryMyInformation();

    UserGroupResponse searchUsers(String query);
}
