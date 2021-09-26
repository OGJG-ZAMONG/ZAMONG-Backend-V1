package app.jg.og.zamong.exception.externalinfra;

import app.jg.og.zamong.exception.ErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExternalInfraException extends RuntimeException {

    private ErrorCode errorCode;

    public ExternalInfraException(String message) {
        super(message);
    }
}
