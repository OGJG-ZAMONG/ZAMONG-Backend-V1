package app.jg.og.zamong.dto.response.dream.sharedream;

import app.jg.og.zamong.dto.response.Response;
import app.jg.og.zamong.dto.response.dream.sharedream.ShareDreamResponse;
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
