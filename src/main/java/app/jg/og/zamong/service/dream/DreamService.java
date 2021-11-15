package app.jg.og.zamong.service.dream;

import app.jg.og.zamong.dto.request.dream.DreamContentRequest;
import app.jg.og.zamong.dto.request.dream.DreamTitleRequest;
import app.jg.og.zamong.dto.request.dream.DreamTypesRequest;
import org.springframework.web.multipart.MultipartFile;

public interface DreamService {

    void patchDreamTitle(String uuid, DreamTitleRequest request);
    void patchDreamContent(String uuid, DreamContentRequest request);
    void patchDreamTypes(String uuid, DreamTypesRequest request);

    void patchDreamImage(String uuid, MultipartFile file);

    void removeDream(String uuid);
}
