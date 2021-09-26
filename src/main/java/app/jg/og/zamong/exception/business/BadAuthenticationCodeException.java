package app.jg.og.zamong.exception.business;

import app.jg.og.zamong.exception.ErrorCode;

public class BadAuthenticationCodeException extends BusinessException {

    public BadAuthenticationCodeException(String message) {
        super(message);

        setErrorCode(ErrorCode.BAD_AUTHENTICATION_CODE);
    }
}
