package app.jg.og.zamong.exception.business;

import app.jg.og.zamong.exception.ErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessException extends RuntimeException {

    private ErrorCode errorCode;

    public BusinessException(String message) {
        super(message);
    }
}
