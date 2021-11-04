package app.jg.og.zamong.exception.business;

import app.jg.og.zamong.exception.ErrorCode;

public class CantCancelSellDreamException extends BusinessException {

    public CantCancelSellDreamException(String message) {
        super(message);

        setErrorCode(ErrorCode.CANT_CANCEL_SELL_DREAM);
    }
}
