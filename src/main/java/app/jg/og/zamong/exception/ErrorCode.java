package app.jg.og.zamong.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    INVALID_PARAMETER(400, "Invalid Parameter"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed");

    private final Integer status;
    private final String message;

    ErrorCode(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
