package app.jg.og.zamong.service.dream.interpretation;

import app.jg.og.zamong.dto.response.Response;
import app.jg.og.zamong.dto.response.dream.interpretationdream.InterpretationDreamCategoryResponse;

import java.util.UUID;

public interface InterpretationDreamService {

    InterpretationDreamCategoryResponse queryInterpretationDreamCategory();
    Response queryInterpretation(String uuid);
}
