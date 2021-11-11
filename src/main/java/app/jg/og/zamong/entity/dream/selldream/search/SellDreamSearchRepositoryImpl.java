package app.jg.og.zamong.entity.dream.selldream.search;

import app.jg.og.zamong.entity.dream.dreamtype.QDreamType;
import app.jg.og.zamong.entity.dream.enums.DreamType;
import app.jg.og.zamong.entity.dream.selldream.QSellDream;
import app.jg.og.zamong.entity.dream.selldream.SellDream;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class SellDreamSearchRepositoryImpl extends QuerydslRepositorySupport implements SellDreamSearchRepository {

    public SellDreamSearchRepositoryImpl() {
        super(SellDream.class);
    }

    @Override
    public List<SellDream> findByTitleLikeAndDreamTypesIn(String title, List<DreamType> dreamTypes) {
        QSellDream sellDream = QSellDream.sellDream;
        QDreamType dreamType = QDreamType.dreamType;

        return from(sellDream)
                .innerJoin(sellDream.dreamTypes, dreamType)
                .where(sellDream.title.contains(title)
                        .and(dreamType.code.in(dreamTypes)))
                .orderBy(sellDream.createdAt.desc())
                .fetch();
    }
}
