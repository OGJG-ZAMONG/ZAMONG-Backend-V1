package app.jg.og.zamong.service.dream.find;

import app.jg.og.zamong.dto.response.*;

public interface ShareDreamFindService {

    ShareDreamInformationResponse queryShareDreamInformation(String uuid);
    SharedDreamGroupResponse queryShareDreams(int page, int size, String sort);
    ShareDreamGroupResponse queryMyShareDreams(int page, int size);
    ShareDreamGroupResponse queryFollowShareDreams(int page, int size);
    ShareDreamTimeTableResponse queryMyShareDreamTimeTable(int year, int month);
    ShareDreamTimeTableResponseV2 queryShareDreamTimeTableV2(int year, int month);
}
