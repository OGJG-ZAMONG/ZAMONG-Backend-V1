package app.jg.og.zamong.exception.externalinfra;

import app.jg.og.zamong.exception.ErrorCode;

public class MailSendFailedException extends ExternalInfraException {

    public MailSendFailedException(String message) {
        super(message);

        setErrorCode(ErrorCode.MAIL_SEND_FAILED);
    }
}
