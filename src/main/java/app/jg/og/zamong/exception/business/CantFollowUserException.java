package app.jg.og.zamong.exception.business;

import app.jg.og.zamong.exception.ErrorCode;

public class CantFollowUserException extends BusinessException {

    public CantFollowUserException(String message) {
        super(message);

        setErrorCode(ErrorCode.CANT_FOLLOW_USER);
    }
}
