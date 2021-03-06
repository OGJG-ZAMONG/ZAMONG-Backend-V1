package app.jg.og.zamong.service.user.auth.signup;

import app.jg.og.zamong.dto.request.*;
import app.jg.og.zamong.dto.response.StringResponse;
import app.jg.og.zamong.dto.response.user.SignUpUserResponse;

public interface UserSignUpService {

    StringResponse checkIdDuplication(CheckIdDuplicationRequest request);
    SignUpUserResponse doSignUpUser(SignUpUserRequest request);
    StringResponse sendOutAuthenticationEmail(EmailAuthenticationRequest request);
}
