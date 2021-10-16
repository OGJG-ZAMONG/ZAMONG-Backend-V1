package app.jg.og.zamong.exception.business;

import app.jg.og.zamong.exception.ErrorCode;

public class AlreadySharedException extends BusinessException {

    public AlreadySharedException(String message) {
        super(message);

        setErrorCode(ErrorCode.ALREADY_SHARED);
    }
}
