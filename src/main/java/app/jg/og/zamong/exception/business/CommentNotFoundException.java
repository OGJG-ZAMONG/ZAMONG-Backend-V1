package app.jg.og.zamong.exception.business;

import app.jg.og.zamong.exception.ErrorCode;

public class CommentNotFoundException extends BusinessException {


    public CommentNotFoundException(String message) {
        super(message);

        setErrorCode(ErrorCode.COMMENT_NOT_FOUND);
    }
}
