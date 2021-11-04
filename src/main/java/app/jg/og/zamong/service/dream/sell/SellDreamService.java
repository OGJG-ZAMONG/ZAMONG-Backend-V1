package app.jg.og.zamong.service.dream.sell;

import app.jg.og.zamong.dto.request.dream.selldream.SellDreamCostRequest;
import app.jg.og.zamong.dto.request.dream.selldream.SellDreamRequest;
import app.jg.og.zamong.dto.response.CreateDreamResponse;
import app.jg.og.zamong.dto.response.dream.selldream.DoSellRequestDreamResponse;

public interface SellDreamService {

    CreateDreamResponse createSellDream(SellDreamRequest request);
    DoSellRequestDreamResponse doSellRequestDream(String uuid);
    void patchSellDreamCost(String uuid, SellDreamCostRequest request);

    void cancelSellDream(String uuid);
}
