package app.jg.og.zamong.service.user;

import app.jg.og.zamong.dto.request.ChangePasswordRequest;
import app.jg.og.zamong.dto.request.CheckIdDuplicationRequest;
import app.jg.og.zamong.dto.response.user.UserInformationResponse;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    UserInformationResponse queryUserInformation(String uuid);
    UserInformationResponse queryMyInformation();
    void modifyProfile(MultipartFile file);
    void modifyUserId(CheckIdDuplicationRequest request);
    void modifyPassword(ChangePasswordRequest request);
}
