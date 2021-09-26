package app.jg.og.zamong.exception.business;

import app.jg.og.zamong.exception.ErrorCode;

public class UserNotFoundException extends BusinessException {

    public UserNotFoundException(String message) {
        super(message);

        setErrorCode(ErrorCode.USER_NOT_FOUND);
    }
}
