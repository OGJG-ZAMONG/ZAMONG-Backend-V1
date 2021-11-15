package app.jg.og.zamong.service.dream.sell;

import app.jg.og.zamong.dto.request.dream.selldream.SellDreamCostRequest;
import app.jg.og.zamong.dto.request.dream.selldream.SellDreamRequest;
import app.jg.og.zamong.dto.response.Response;
import app.jg.og.zamong.dto.response.dream.CreateDreamResponse;
import app.jg.og.zamong.dto.response.dream.selldream.DoSellRequestDreamResponse;

public interface SellDreamService {

    CreateDreamResponse createSellDream(SellDreamRequest request);
    DoSellRequestDreamResponse doSellRequestDream(String uuid);
    void patchSellDreamCost(String uuid, SellDreamCostRequest request);

    void doneSellDream(String uuid);
    void cancelSellDream(String uuid);

    Response acceptSellDreamRequest(String uuid);
}
