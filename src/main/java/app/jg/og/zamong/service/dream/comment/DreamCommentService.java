package app.jg.og.zamong.service.dream.comment;

import app.jg.og.zamong.dto.request.DreamCommentRecommendRequest;
import app.jg.og.zamong.dto.request.dream.DreamCommentRequest;
import app.jg.og.zamong.dto.response.NumberResponse;
import app.jg.og.zamong.dto.response.dream.comment.DreamCommendGroupResponse;
import app.jg.og.zamong.dto.response.dream.comment.DreamCommentResponse;

public interface DreamCommentService {

    DreamCommentResponse createDream(String dreamId, DreamCommentRequest request);
    DreamCommendGroupResponse queryDreamComment(String uuid);
    DreamCommendGroupResponse queryDreamReComment(String uuid);
    void deleteDreamComment(String uuid);

    void doCommentRecommend(String uuid, DreamCommentRecommendRequest request);
    void patchCommentContent(String uuid, DreamCommentRequest  request);

    NumberResponse queryCountOfComment(String uuid);

    void checkDreamComment(String uuid);
}
