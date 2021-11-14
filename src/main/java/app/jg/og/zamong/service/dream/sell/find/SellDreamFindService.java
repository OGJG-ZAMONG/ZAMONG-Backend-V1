package app.jg.og.zamong.service.dream.sell.find;

import app.jg.og.zamong.dto.response.dream.selldream.SellDreamGroupResponse;
import app.jg.og.zamong.dto.response.dream.selldream.SellDreamInformationResponse;

public interface SellDreamFindService {

    SellDreamGroupResponse queryPendingSellDreams(int page, int size);
    SellDreamGroupResponse queryMyPendingSellDreams(int page, int size);
    SellDreamGroupResponse queryMyClosedSellDream(int page, int size);

    SellDreamGroupResponse searchSellDream(String title, String[] types);

    SellDreamInformationResponse querySellDreamInformation(String uuid);
}
