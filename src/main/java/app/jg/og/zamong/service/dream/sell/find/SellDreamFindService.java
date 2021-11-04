package app.jg.og.zamong.service.dream.sell.find;

import app.jg.og.zamong.dto.response.dream.selldream.SellDreamGroupResponse;

public interface SellDreamFindService {

    SellDreamGroupResponse queryPendingSellDreams(int page, int size);
    SellDreamGroupResponse queryClosedSellDream(int page, int size);
}
