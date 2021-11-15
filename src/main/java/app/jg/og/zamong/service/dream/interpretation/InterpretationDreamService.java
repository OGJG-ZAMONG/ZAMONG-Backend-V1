package app.jg.og.zamong.service.dream.interpretation;

import app.jg.og.zamong.dto.request.dream.interpretationdream.InterpretationDreamRequest;
import app.jg.og.zamong.dto.response.dream.CreateDreamResponse;
import app.jg.og.zamong.dto.response.Response;
import app.jg.og.zamong.dto.response.dream.interpretationdream.InterpretationDreamCategoryResponse;

public interface InterpretationDreamService {

    CreateDreamResponse createInterpretationDream(InterpretationDreamRequest request);
    CreateDreamResponse modifyInterpretationDream(String uuid, InterpretationDreamRequest request);

    InterpretationDreamCategoryResponse queryInterpretationDreamCategory();
    Response queryInterpretation(String uuid);
}
