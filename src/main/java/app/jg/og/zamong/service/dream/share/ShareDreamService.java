package app.jg.og.zamong.service.dream.share;

import app.jg.og.zamong.dto.request.dream.sharedream.ShareDreamQualityRequest;
import app.jg.og.zamong.dto.request.dream.sharedream.ShareDreamRequest;
import app.jg.og.zamong.dto.request.dream.sharedream.ShareDreamSleepDateTimeRequest;
import app.jg.og.zamong.dto.response.CreateDreamResponse;
import app.jg.og.zamong.dto.response.dream.sharedream.DoShareDreamResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ShareDreamService {

    CreateDreamResponse createShareDream(ShareDreamRequest request);
    CreateDreamResponse modifyShareDream(String uuid, ShareDreamRequest request);
    void patchShareDreamImage(String uuid, MultipartFile file);
    void patchShareDreamQuality(String uuid, ShareDreamQualityRequest request);
    void patchShareDreamSleepDateTime(String uuid, ShareDreamSleepDateTimeRequest request);

    DoShareDreamResponse doShareDream(String uuid);
    void addShareDreamLucy(String uuid);
    void cancelShareDreamLucy(String uuid);
}
