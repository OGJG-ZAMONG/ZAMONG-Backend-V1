package app.jg.og.zamong.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ResponseBody {

    private final Integer status;
    private final LocalDateTime timestamp;
    private final Content content;

    @Builder
    @Getter
    public static class Content {

        private final Boolean collectionValue;
        private final Response response;
    }

    public static ResponseBody of(Response response, Integer status) {
        return ResponseBody.builder()
                .status(status)
                .timestamp(LocalDateTime.now())
                .content(Content.builder()
                    .collectionValue(false)
                    .response(response)
                    .build())
                .build();
    }

    public static ResponseBody listOf(Response response, Integer status) {
        return ResponseBody.builder()
                .status(status)
                .timestamp(LocalDateTime.now())
                .content(Content.builder()
                        .collectionValue(true)
                        .response(response)
                        .build())
                .build();
    }
}
