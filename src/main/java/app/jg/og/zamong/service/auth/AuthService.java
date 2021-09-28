package app.jg.og.zamong.service.auth;

import app.jg.og.zamong.dto.request.CheckIdDuplicationRequest;
import app.jg.og.zamong.dto.request.EmailAuthenticationRequest;
import app.jg.og.zamong.dto.request.LoginUserRequest;
import app.jg.og.zamong.dto.request.SignUpUserRequest;
import app.jg.og.zamong.dto.response.IssueTokenResponse;
import app.jg.og.zamong.dto.response.StringResponse;
import app.jg.og.zamong.dto.response.SignedUserResponse;

public interface AuthService {
    StringResponse checkIdDuplication(CheckIdDuplicationRequest request);
    SignedUserResponse registerUser(SignUpUserRequest request);
    IssueTokenResponse loginUser(LoginUserRequest request);
    StringResponse sendOutAuthenticationEmail(EmailAuthenticationRequest request);
}
