package app.jg.og.zamong.service.user;

import app.jg.og.zamong.dto.request.ChangePasswordRequest;
import app.jg.og.zamong.dto.request.CheckIdDuplicationRequest;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    void modifyProfile(MultipartFile file);
    void modifyUserId(CheckIdDuplicationRequest request);
    void modifyPassword(ChangePasswordRequest request);
}
