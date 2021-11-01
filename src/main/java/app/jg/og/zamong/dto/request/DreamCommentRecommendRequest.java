package app.jg.og.zamong.dto.request;

import app.jg.og.zamong.entity.dream.comment.recommend.RecommendType;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DreamCommentRecommendRequest {

    private RecommendType type;
}
