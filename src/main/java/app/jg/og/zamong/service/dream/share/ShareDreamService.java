package app.jg.og.zamong.service.dream.share;

import app.jg.og.zamong.dto.request.dream.DreamContentRequest;
import app.jg.og.zamong.dto.request.dream.DreamTitleRequest;
import app.jg.og.zamong.dto.request.dream.DreamTypesRequest;
import app.jg.og.zamong.dto.request.dream.sharedream.ShareDreamQualityRequest;
import app.jg.og.zamong.dto.request.dream.sharedream.ShareDreamRequest;
import app.jg.og.zamong.dto.request.dream.sharedream.ShareDreamSleepDateTimeRequest;
import app.jg.og.zamong.dto.response.CreateShareDreamResponse;

public interface ShareDreamService {

    CreateShareDreamResponse createShareDream(ShareDreamRequest request);
    CreateShareDreamResponse modifyShareDream(String uuid, ShareDreamRequest request);
    void patchShareDreamQuality(String uuid, ShareDreamQualityRequest request);
    void patchShareDreamSleepDateTime(String uuid, ShareDreamSleepDateTimeRequest request);

    void patchDreamTitle(String uuid, DreamTitleRequest request);
    void patchDreamContent(String uuid, DreamContentRequest request);
    void patchDreamTypes(String uuid, DreamTypesRequest request);
}
