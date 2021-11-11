package app.jg.og.zamong.entity.dream.selldream.search;

import app.jg.og.zamong.entity.dream.enums.DreamType;
import app.jg.og.zamong.entity.dream.selldream.SellDream;

import java.util.List;

public interface SellDreamSearchRepository {

    List<SellDream> findByTitleLikeAndDreamTypesIn(String title, List<DreamType> dreamTypes);
}
