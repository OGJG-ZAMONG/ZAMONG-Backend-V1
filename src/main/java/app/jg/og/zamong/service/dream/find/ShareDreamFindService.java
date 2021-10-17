package app.jg.og.zamong.service.dream.find;

import app.jg.og.zamong.dto.response.SharedDreamGroupResponse;

public interface ShareDreamFindService {

    SharedDreamGroupResponse queryShareDreams(int page, int size);
    SharedDreamGroupResponse queryMyShareDreams(int page, int size);
}
