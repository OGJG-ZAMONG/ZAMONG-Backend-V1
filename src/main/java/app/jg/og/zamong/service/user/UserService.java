package app.jg.og.zamong.service.user;

import app.jg.og.zamong.dto.response.UserInformationResponse;

public interface UserService {
    UserInformationResponse queryUserInformation(String uuid);
    UserInformationResponse queryMyInformation();
}
