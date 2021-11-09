package app.jg.og.zamong.entity.dream.sharedream.search;

import app.jg.og.zamong.entity.dream.enums.DreamType;
import app.jg.og.zamong.entity.dream.sharedream.ShareDream;

import java.util.List;

public interface ShareDreamSearchRepository {

    List<ShareDream> findByTitleLikeAndDreamTypesIn(String title, List<DreamType> dreamTypes);
}
