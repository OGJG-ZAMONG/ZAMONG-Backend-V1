package app.jg.og.zamong.service.dream;

import app.jg.og.zamong.dto.request.ShareDreamRequest;
import app.jg.og.zamong.dto.response.ShareDreamResponse;

public interface DreamService {
    ShareDreamResponse createShareDream(ShareDreamRequest request);
    ShareDreamResponse modifyShareDream(String uuid, ShareDreamRequest request);
}
