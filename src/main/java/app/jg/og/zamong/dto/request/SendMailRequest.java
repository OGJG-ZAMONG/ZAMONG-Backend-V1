package app.jg.og.zamong.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SendMailRequest {

    private final String address;

    private final String authenticationCode;

    private final String title;
}
