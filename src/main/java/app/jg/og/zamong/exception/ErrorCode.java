package app.jg.og.zamong.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    INVALID_PARAMETER(400, "Invalid Parameter"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),

    USER_NOT_FOUND(400, "User Not Found"),
    DREAM_NOT_FOUND(400, "Dream Not Found"),
    USER_IDENTITY_DUPLICATION(400, "User Identity is Duplication"),
    BAD_AUTHENTICATION_CODE(400, "Bad Authentication Code"),
    BAD_USER_INFORMATION(400, "Bad User Information"),
    UNAUTHORIZED_TOKEN(401, "Unauthorized Token"),
    ALREADY_SHARED(403, "Already Shared Dream"),
    NOT_SHARED(403, "Not Shared Dream"),

    MAIL_SEND_FAILED(400, "Mail Send Failed"),
    ATTRIBUTE_CONVERT_FAILED(400, "Attribute Convert Failed"),

    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    private final Integer status;
    private final String message;

    ErrorCode(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
