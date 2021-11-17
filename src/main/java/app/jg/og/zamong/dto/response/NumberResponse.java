package app.jg.og.zamong.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NumberResponse implements Response {

    private final Integer number;
}
