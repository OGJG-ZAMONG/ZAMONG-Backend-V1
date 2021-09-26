package app.jg.og.zamong.exception.business;

import app.jg.og.zamong.exception.ErrorCode;

public class BadUserInformationException extends BusinessException {

    public BadUserInformationException(String message) {
        super(message);

        setErrorCode(ErrorCode.BAD_USER_INFORMATION);
    }
}
