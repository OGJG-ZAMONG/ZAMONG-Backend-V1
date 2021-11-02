package app.jg.og.zamong.service.dream.sell;

import app.jg.og.zamong.dto.request.dream.selldream.SellDreamRequest;
import app.jg.og.zamong.dto.response.CreateDreamResponse;

public interface SellDreamService {

    CreateDreamResponse createSellDream(SellDreamRequest request);
}
