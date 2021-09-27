package app.jg.og.zamong.service.auth;

import app.jg.og.zamong.dto.request.EmailAuthenticationRequest;
import app.jg.og.zamong.dto.request.LoginUserRequest;
import app.jg.og.zamong.dto.request.SignUpUserRequest;
import app.jg.og.zamong.dto.response.IssueTokenResponse;
import app.jg.og.zamong.dto.response.MailSentResponse;
import app.jg.og.zamong.dto.response.SignedUserResponse;

public interface AuthService {
    SignedUserResponse registerUser(SignUpUserRequest request);
    IssueTokenResponse loginUser(LoginUserRequest request);
    MailSentResponse sendOutAuthenticationEmail(EmailAuthenticationRequest request);
}
