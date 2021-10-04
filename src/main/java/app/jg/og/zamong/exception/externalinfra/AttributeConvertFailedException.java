package app.jg.og.zamong.exception.externalinfra;

import app.jg.og.zamong.exception.ErrorCode;

public class AttributeConvertFailedException extends ExternalInfraException {

    public AttributeConvertFailedException(String message) {
        super(message);

        setErrorCode(ErrorCode.ATTRIBUTE_CONVERT_FAILED);
    }
}
