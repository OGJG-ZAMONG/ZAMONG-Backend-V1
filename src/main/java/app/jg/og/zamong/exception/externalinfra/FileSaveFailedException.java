package app.jg.og.zamong.exception.externalinfra;

import app.jg.og.zamong.exception.ErrorCode;

public class FileSaveFailedException extends ExternalInfraException {


    public FileSaveFailedException() {
        super("File Save Failed");

        setErrorCode(ErrorCode.FILE_SAVE_FAILED);
    }
}
