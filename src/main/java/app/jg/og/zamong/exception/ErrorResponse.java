package app.jg.og.zamong.exception;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {

    private String message;
    private Integer status;
    private String description;

    public static ErrorResponse of(ErrorCode errorCode, String description) {
        return ErrorResponse.builder()
                .message(errorCode.getMessage())
                .status(errorCode.getStatus())
                .description(description)
                .build();
    }
}
