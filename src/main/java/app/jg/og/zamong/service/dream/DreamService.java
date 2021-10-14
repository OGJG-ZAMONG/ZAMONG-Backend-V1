package app.jg.og.zamong.service.dream;

import app.jg.og.zamong.dto.request.dream.DreamContentRequest;
import app.jg.og.zamong.dto.request.dream.DreamTitleRequest;
import app.jg.og.zamong.dto.request.dream.DreamTypesRequest;

public interface DreamService {

    void patchDreamTitle(String uuid, DreamTitleRequest request);
    void patchDreamContent(String uuid, DreamContentRequest request);
    void patchDreamTypes(String uuid, DreamTypesRequest request);

    void removeDream(String uuid);
}
