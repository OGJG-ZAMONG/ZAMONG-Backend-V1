package app.jg.og.zamong.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ShareDreamGroupResponse {

    private final List<ShareDreamResponse> shareDreams;

    private final Integer total_page;
}
