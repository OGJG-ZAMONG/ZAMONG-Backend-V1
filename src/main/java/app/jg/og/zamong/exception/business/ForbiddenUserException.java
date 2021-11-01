package app.jg.og.zamong.exception.business;

import app.jg.og.zamong.exception.ErrorCode;

public class ForbiddenUserException extends BusinessException {

    public ForbiddenUserException(String message) {
        super(message);

        setErrorCode(ErrorCode.FORBIDDEN_USER);
    }
}
