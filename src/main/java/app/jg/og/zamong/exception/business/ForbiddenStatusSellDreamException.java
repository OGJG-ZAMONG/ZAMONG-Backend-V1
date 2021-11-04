package app.jg.og.zamong.exception.business;

import app.jg.og.zamong.exception.ErrorCode;

public class ForbiddenStatusSellDreamException extends BusinessException {

    public ForbiddenStatusSellDreamException(String message) {
        super(message);

        setErrorCode(ErrorCode.FORBIDEN_STATUS_SELL_DREAM);
    }
}
