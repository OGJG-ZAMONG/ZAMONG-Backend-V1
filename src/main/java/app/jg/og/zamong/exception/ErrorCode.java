package app.jg.og.zamong.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    INVALID_PARAMETER(400, "Invalid Parameter"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),

    USER_NOT_FOUND(400, "User Not Found"),
    USER_IDENTITY_DUPLICATION(400, "User Identity is Duplication"),
    BAD_AUTHENTICATION_CODE(400, "Bad Authentication Code"),
    BAD_USER_INFORMATION(400, "Bad User Information"),

    MAIL_SEND_FAILED(400, "Mail Send Failed");

    private final Integer status;
    private final String message;

    ErrorCode(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
