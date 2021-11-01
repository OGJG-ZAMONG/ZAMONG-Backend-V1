package app.jg.og.zamong.service.dream.comment;

import app.jg.og.zamong.dto.request.DreamCommentRecommendRequest;
import app.jg.og.zamong.dto.request.dream.DreamCommentRequest;
import app.jg.og.zamong.dto.response.DreamCommendGroupResponse;
import app.jg.og.zamong.dto.response.DreamCommentResponse;

public interface DreamCommentService {

    DreamCommentResponse createDream(String dreamId, DreamCommentRequest request);
    DreamCommendGroupResponse queryDreamComment(String uuid);
    DreamCommendGroupResponse queryDreamReComment(String uuid);
    void doCommentRecommend(String uuid, DreamCommentRecommendRequest request);
    void patchCommentContent(String uuid, DreamCommentRequest  request);
}
