package app.jg.og.zamong.service.user;

import app.jg.og.zamong.dto.response.UserInformationResponse;
import app.jg.og.zamong.entity.user.User;

public interface UserService {

    User queryUser(String uuid);
    UserInformationResponse queryUserInformation(User user);
}
