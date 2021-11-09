package app.jg.og.zamong.entity.dream.sharedream.search;

import app.jg.og.zamong.entity.dream.dreamtype.QDreamType;
import app.jg.og.zamong.entity.dream.enums.DreamType;
import app.jg.og.zamong.entity.dream.sharedream.QShareDream;
import app.jg.og.zamong.entity.dream.sharedream.ShareDream;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class ShareDreamSearchRepositoryImpl extends QuerydslRepositorySupport implements ShareDreamSearchRepository{

    public ShareDreamSearchRepositoryImpl() {
        super(ShareDream.class);
    }

    @Override
    public List<ShareDream> findByTitleLikeAndDreamTypesIn(String title, List<DreamType> dreamTypes) {
        QShareDream shareDream = QShareDream.shareDream;
        QDreamType dreamType = QDreamType.dreamType;

        return from(shareDream)
                .innerJoin(shareDream.dreamTypes, dreamType)
                .where(shareDream.title.contains(title)
                        .and(shareDream.isShared.isTrue()
                                .and(dreamType.code.in(dreamTypes))))
                .orderBy(shareDream.createdAt.desc())
                .fetch();
    }
}
