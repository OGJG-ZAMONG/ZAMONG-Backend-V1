package app.jg.og.zamong.service.dream;

import app.jg.og.zamong.dto.request.CreateShareDreamRequest;
import app.jg.og.zamong.dto.response.CreateShareDreamResponse;

public interface DreamService {
    CreateShareDreamResponse createShareDream(CreateShareDreamRequest request);
}
