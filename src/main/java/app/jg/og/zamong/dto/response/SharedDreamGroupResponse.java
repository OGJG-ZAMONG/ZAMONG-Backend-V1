package app.jg.og.zamong.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class SharedDreamGroupResponse implements Response {

    private final List<SharedDreamResponse> shareDreams;

    private final Integer totalPage;

    private final Long totalSize;
}
