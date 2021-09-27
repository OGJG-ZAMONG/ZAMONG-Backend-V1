package app.jg.og.zamong.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MailSentResponse implements Response {

    private final String message;

    @Override
    public String toString() {
        return message;
    }
}
