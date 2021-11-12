package app.jg.og.zamong.dto.response.dream.interpretationdream;

import app.jg.og.zamong.dto.response.Response;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class InterpretationDreamGroupResponse implements Response {

    private final List<InterpretationDreamResponse> interpretationDreams;

    private final Integer totalPage;

    private final Long totalSize;
}
