package app.jg.og.zamong.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Builder
@Getter
public class ShareDreamTimeTableResponse implements Response {

    private final Map<LocalDate, List<ShareDreamResponse>> timetables;
}
