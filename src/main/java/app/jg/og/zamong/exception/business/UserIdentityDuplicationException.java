package app.jg.og.zamong.exception.business;

import app.jg.og.zamong.exception.ErrorCode;

public class UserIdentityDuplicationException extends BusinessException {

    public UserIdentityDuplicationException(String message) {
        super(message);

        setErrorCode(ErrorCode.USER_IDENTITY_DUPLICATION);
    }
}
