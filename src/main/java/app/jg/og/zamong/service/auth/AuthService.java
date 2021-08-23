package app.jg.og.zamong.service.auth;

import app.jg.og.zamong.dto.request.UserSignUpRequest;
import app.jg.og.zamong.entity.user.User;

public interface AuthService {
    User createUser(UserSignUpRequest request);
}
