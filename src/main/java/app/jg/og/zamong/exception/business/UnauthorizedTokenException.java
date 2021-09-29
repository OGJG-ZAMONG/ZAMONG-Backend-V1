package app.jg.og.zamong.exception.business;

import app.jg.og.zamong.exception.ErrorCode;

public class UnauthorizedTokenException extends BusinessException {


    public UnauthorizedTokenException(String message) {
        super(message);

        setErrorCode(ErrorCode.UNAUTHORIZED_TOKEN);
    }
}
