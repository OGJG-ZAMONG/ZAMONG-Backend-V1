package app.jg.og.zamong.service.dream.share.find;

import app.jg.og.zamong.dto.response.dream.sharedream.*;
import app.jg.og.zamong.entity.dream.enums.DreamType;

import java.util.List;

public interface ShareDreamFindService {

    ShareDreamInformationResponse queryShareDreamInformation(String uuid);
    SharedDreamGroupResponse queryShareDreams(int page, int size, String sort);
    ShareDreamGroupResponse queryMyShareDreams(int page, int size, String sort);
    ShareDreamGroupResponse queryTodayMyShareDreams();
    ShareDreamGroupResponse queryFollowShareDreams(int page, int size);
    ShareDreamTimeTableResponse queryMyShareDreamTimeTable(int year, int month);
    ShareDreamTimeTableResponseV2 queryShareDreamTimeTableV2(int year, int month);
    SharedDreamGroupResponse querySearchShareDreams(String title, String[] types);
}
