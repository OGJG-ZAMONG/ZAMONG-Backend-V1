package app.jg.og.zamong.entity.dream.interpretationdream.search;

import app.jg.og.zamong.entity.dream.enums.DreamType;
import app.jg.og.zamong.entity.dream.interpretationdream.InterpretationDream;

import java.util.List;

public interface InterpretationDreamSearchRepository {

    List<InterpretationDream> findByTitleLikeAndDreamTypesIn(String title, List<DreamType> dreamTypes);
}
