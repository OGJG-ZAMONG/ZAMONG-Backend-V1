package app.jg.og.zamong.service.dream.find;

import app.jg.og.zamong.dto.response.ShareDreamGroupResponse;

public interface DreamFindService {

    ShareDreamGroupResponse queryShareDreams(int page, int size);
}
