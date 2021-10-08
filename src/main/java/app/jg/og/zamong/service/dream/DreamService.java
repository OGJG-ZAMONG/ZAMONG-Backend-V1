package app.jg.og.zamong.service.dream;

import app.jg.og.zamong.dto.request.dream.DreamTitleRequest;
import app.jg.og.zamong.dto.request.dream.sharedream.ShareDreamQualityRequest;
import app.jg.og.zamong.dto.request.dream.sharedream.ShareDreamRequest;
import app.jg.og.zamong.dto.request.dream.sharedream.ShareDreamSleepDateTimeRequest;
import app.jg.og.zamong.dto.response.ShareDreamResponse;

public interface DreamService {
    ShareDreamResponse createShareDream(ShareDreamRequest request);
    ShareDreamResponse modifyShareDream(String uuid, ShareDreamRequest request);
    void patchShareDreamQuality(String uuid, ShareDreamQualityRequest request);
    void patchShareDreamSleepDateTime(String uuid, ShareDreamSleepDateTimeRequest request);

    void patchDreamTitle(String uuid, DreamTitleRequest request);
}
