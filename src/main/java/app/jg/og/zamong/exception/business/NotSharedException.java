package app.jg.og.zamong.exception.business;

import app.jg.og.zamong.exception.ErrorCode;

public class NotSharedException extends BusinessException {

    public NotSharedException(String message) {
        super(message);

        setErrorCode(ErrorCode.NOT_SHARED);
    }
}
