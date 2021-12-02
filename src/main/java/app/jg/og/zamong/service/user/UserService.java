package app.jg.og.zamong.service.user;

import app.jg.og.zamong.dto.request.ChangePasswordRequest;
import app.jg.og.zamong.dto.request.CheckIdDuplicationRequest;
import app.jg.og.zamong.dto.request.ResetPasswordRequest;
import app.jg.og.zamong.dto.request.dream.LeaveUserRequest;
import app.jg.og.zamong.dto.response.StringResponse;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    void modifyProfile(MultipartFile file);
    void modifyUserId(CheckIdDuplicationRequest request);
    void modifyPassword(ChangePasswordRequest request);
    void resetPassword(ResetPasswordRequest request);
    void leaveUser(LeaveUserRequest request);

    StringResponse sendFindPasswordEmail(String email);
}
