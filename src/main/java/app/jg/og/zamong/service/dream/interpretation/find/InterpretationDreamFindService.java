package app.jg.og.zamong.service.dream.interpretation.find;

import app.jg.og.zamong.dto.response.dream.interpretationdream.InterpretationDreamGroupResponse;
import app.jg.og.zamong.dto.response.dream.interpretationdream.InterpretationDreamInformationResponse;

public interface InterpretationDreamFindService {

    InterpretationDreamGroupResponse queryInterpretationDreams(int page, int size);
    InterpretationDreamGroupResponse searchInterpretationDreams(String title, String[] types);

    InterpretationDreamInformationResponse queryInterpretationDreamResponse(String uuid);
}
