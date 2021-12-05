package app.jg.og.zamong.entity.dream.interpretationdream.search;

import app.jg.og.zamong.entity.dream.dreamtype.QDreamType;
import app.jg.og.zamong.entity.dream.enums.DreamType;
import app.jg.og.zamong.entity.dream.interpretationdream.InterpretationDream;
import app.jg.og.zamong.entity.dream.interpretationdream.QInterpretationDream;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class InterpretationDreamSearchRepositoryImpl extends QuerydslRepositorySupport implements InterpretationDreamSearchRepository {

    public InterpretationDreamSearchRepositoryImpl() {
        super(InterpretationDream.class);
    }

    @Override
    public List<InterpretationDream> findByTitleLikeAndDreamTypesIn(String title, List<DreamType> dreamTypes) {
        QInterpretationDream interpretationDream = QInterpretationDream.interpretationDream;
        QDreamType dreamType = QDreamType.dreamType;

        return from(interpretationDream)
                .innerJoin(interpretationDream.dreamTypes, dreamType)
                .where(interpretationDream.title.contains(title)
                        .and(dreamType.code.in(dreamTypes)))
                .orderBy(interpretationDream.createdAt.desc())
                .distinct()
                .fetch();
    }
}
