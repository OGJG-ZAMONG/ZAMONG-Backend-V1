package app.jg.og.zamong.dto.response.dream.selldream;

import app.jg.og.zamong.dto.response.Response;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
public class SellDreamRequestGroupResponse implements Response {

    private final List<Request> requests;

    @Builder
    @Getter
    public static class Request {

        private final UUID uuid;

        private final UUID userUuid;

        private final String id;

        private final String profile;

        private final Boolean isAccept;

        @JsonProperty("request_datetime")
        private final LocalDateTime requestDateTime;
    }
}
