package app.jg.og.zamong.service.dream.find;

import app.jg.og.zamong.dto.response.ShareDreamGroupResponse;
import app.jg.og.zamong.dto.response.ShareDreamTimeTableResponse;
import app.jg.og.zamong.dto.response.SharedDreamGroupResponse;

public interface ShareDreamFindService {

    SharedDreamGroupResponse queryShareDreams(int page, int size);
    ShareDreamGroupResponse queryMyShareDreams(int page, int size);
    ShareDreamTimeTableResponse queryMyShareDreamTimeTable(int year, int month);
}
