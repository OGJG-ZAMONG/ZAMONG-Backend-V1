package app.jg.og.zamong.service.auth;

import app.jg.og.zamong.dto.request.EmailAuthenticationRequest;
import app.jg.og.zamong.dto.request.LoginUserRequest;
import app.jg.og.zamong.dto.request.SignUpUserRequest;
import app.jg.og.zamong.dto.response.IssueTokenResponse;
import app.jg.og.zamong.entity.user.User;

public interface AuthService {
    User registerUser(SignUpUserRequest request);
    IssueTokenResponse loginUser(LoginUserRequest request);
    void sendOutAuthenticationEmail(EmailAuthenticationRequest request);
}
