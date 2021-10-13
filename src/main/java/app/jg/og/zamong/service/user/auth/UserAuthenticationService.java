package app.jg.og.zamong.service.user.auth;

import app.jg.og.zamong.dto.request.LoginUserRequest;
import app.jg.og.zamong.dto.request.ReIssueTokenRequest;
import app.jg.og.zamong.dto.response.IssueTokenResponse;

public interface UserAuthenticationService {

    IssueTokenResponse loginUser(LoginUserRequest request);
    IssueTokenResponse refreshToken(ReIssueTokenRequest request);
}
