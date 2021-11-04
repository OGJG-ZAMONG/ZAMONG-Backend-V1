package app.jg.og.zamong.dto.response.dream.selldream;

import app.jg.og.zamong.dto.response.Response;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class SellDreamGroupResponse implements Response {

    private final List<SellDreamResponse> sellDreams;

    private final Integer totalPage;

    private final Long totalSize;
}
