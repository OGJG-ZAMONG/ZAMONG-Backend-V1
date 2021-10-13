package app.jg.og.zamong.service.user.auth;

import app.jg.og.zamong.dto.request.*;
import app.jg.og.zamong.dto.response.IssueTokenResponse;
import app.jg.og.zamong.dto.response.StringResponse;
import app.jg.og.zamong.dto.response.SignUpUserResponse;

public interface UserSignUpService {
    StringResponse checkIdDuplication(CheckIdDuplicationRequest request);
    SignUpUserResponse registerUser(SignUpUserRequest request);
    IssueTokenResponse loginUser(LoginUserRequest request);
    StringResponse sendOutAuthenticationEmail(EmailAuthenticationRequest request);
    IssueTokenResponse refreshToken(ReIssueTokenRequest request);
}
