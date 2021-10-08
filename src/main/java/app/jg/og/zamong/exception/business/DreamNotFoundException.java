package app.jg.og.zamong.exception.business;

import app.jg.og.zamong.exception.ErrorCode;

public class DreamNotFoundException extends BusinessException {

    public DreamNotFoundException(String message) {
        super(message);

        setErrorCode(ErrorCode.DREAM_NOT_FOUND);
    }
}
