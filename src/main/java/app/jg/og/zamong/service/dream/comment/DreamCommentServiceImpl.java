package app.jg.og.zamong.service.dream.comment;

import app.jg.og.zamong.dto.request.DreamCommentRecommendRequest;
import app.jg.og.zamong.dto.request.dream.DreamCommentRequest;
import app.jg.og.zamong.dto.response.NumberResponse;
import app.jg.og.zamong.dto.response.dream.comment.DreamCommendGroupResponse;
import app.jg.og.zamong.dto.response.dream.comment.DreamCommentResponse;
import app.jg.og.zamong.dto.response.user.UserInformationResponse;
import app.jg.og.zamong.entity.dream.Dream;
import app.jg.og.zamong.entity.dream.DreamRepository;
import app.jg.og.zamong.entity.dream.comment.Comment;
import app.jg.og.zamong.entity.dream.comment.CommentRepository;
import app.jg.og.zamong.entity.dream.comment.recommend.Recommend;
import app.jg.og.zamong.entity.dream.comment.recommend.RecommendRepository;
import app.jg.og.zamong.entity.dream.comment.recommend.RecommendType;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.exception.business.CommentNotFoundException;
import app.jg.og.zamong.exception.business.DreamNotFoundException;
import app.jg.og.zamong.exception.business.ForbiddenUserException;
import app.jg.og.zamong.service.dream.comment.filtering.DreamCommentFilteringService;
import app.jg.og.zamong.service.securitycontext.SecurityContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DreamCommentServiceImpl implements DreamCommentService {

    private final DreamRepository dreamRepository;
    private final CommentRepository commentRepository;
    private final RecommendRepository recommendRepository;

    private final SecurityContextService securityContextService;
    private final DreamCommentFilteringService dreamCommentFilteringService;

    @Override
    public DreamCommentResponse createDream(String dreamId, DreamCommentRequest request) {
        User user = securityContextService.getPrincipal().getUser();

        Dream dream = dreamRepository.findById(UUID.fromString(dreamId))
                .orElseThrow(() -> new DreamNotFoundException("해당하는 꿈을 찾을 수 없습니다"));

        Comment pComment = request.getPComment() == null ? null :
                commentRepository.findById(UUID.fromString(request.getPComment()))
                .orElseThrow(() -> new CommentNotFoundException("상위 댓글을 찾을 수 없습니다"));

        Integer depth = pComment == null ? 0 : pComment.getDepth() + 1;

        Comment comment = commentRepository.save(Comment.builder()
                .content(request.getContent())
                .isChecked(false)
                .dateTime(LocalDateTime.now())
                .depth(depth)
                .isAnonymous(request.getIsAnonymous())
                .pComment(pComment)
                .user(user)
                .dream(dream)
                .build());

        return DreamCommentResponse.builder()
                .uuid(comment.getUuid())
                .content(dreamCommentFilteringService.filteringComment(comment.getContent()))
                .user(UserInformationResponse.builder()
                        .uuid(user.getUuid())
                        .name(user.getName())
                        .profile(user.getProfile())
                        .build())
                .depth(depth)
                .build();
    }

    @Override
    public DreamCommendGroupResponse queryDreamComment(String uuid) {
        User user = securityContextService.getPrincipal().getUser();

        Dream dream = dreamRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new DreamNotFoundException("해당하는 꿈을 찾을 수 없습니다"));

        List<DreamCommendGroupResponse.CommentResponse> comments = dream.getComments().stream()
                .filter(comment -> comment.getDepth() == 0)
                .map(comment -> convert(comment, user))
                .collect(Collectors.toList());

        return DreamCommendGroupResponse.builder()
                .comments(comments)
                .build();
    }

    @Override
    public DreamCommendGroupResponse queryDreamReComment(String uuid) {
        User user = securityContextService.getPrincipal().getUser();

        Comment pComment = commentRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new CommentNotFoundException("댓글을 찾을 수 없습니다"));

        List<DreamCommendGroupResponse.CommentResponse> comments = pComment.getComments().stream()
                .filter(comment -> comment.getDepth() == pComment.getDepth() + 1)
                .map(comment -> convert(comment, user))
                .collect(Collectors.toList());

        return DreamCommendGroupResponse.builder()
                .comments(comments)
                .build();
    }

    @Override
    public void deleteDreamComment(String uuid) {
        User user = securityContextService.getPrincipal().getUser();

        Comment comment = commentRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new CommentNotFoundException("댓글을 찾을 수 없습니다"));

        if(!comment.getUser().equals(user)) {
            throw new ForbiddenUserException("작성자가 아닙니다");
        }

        commentRepository.delete(comment);
    }

    private DreamCommendGroupResponse.CommentResponse convert(Comment comment, User user) {
        return DreamCommendGroupResponse.CommentResponse.builder()
                .uuid(comment.getUuid())
                .isChecked(comment.getIsChecked())
                .content(dreamCommentFilteringService.filteringComment(comment.getContent()))
                .dateTime(comment.getDateTime())
                .userUuid(comment.getUser().getUuid())
                .userProfile(comment.getUser().getProfile())
                .likeCount(recommendRepository.countAllByCommentAndRecommendType(comment, RecommendType.LIKE))
                .dislikeCount(recommendRepository.countAllByCommentAndRecommendType(comment, RecommendType.DISLIKE))
                .isLike(recommendRepository.existsByCommentAndUserAndRecommendType(comment, user, RecommendType.LIKE))
                .isDisLike(recommendRepository.existsByCommentAndUserAndRecommendType(comment, user, RecommendType.DISLIKE))
                .itsMe(comment.getUser().equals(user))
                .build();
    }

    @Override
    public void doCommentRecommend(String uuid, DreamCommentRecommendRequest request) {
        User user = securityContextService.getPrincipal().getUser();

        Comment comment = commentRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new CommentNotFoundException("댓글을 찾을 수 없습니다"));

        Recommend recommend = recommendRepository.findByCommentAndUser(comment, user)
                .orElse(null);

        if(recommend != null) {
            recommendRepository.delete(recommend);
            return;
        }

        recommendRepository.save(Recommend.builder()
                .recommendType(request.getType())
                .user(user)
                .comment(comment)
                .build());
    }

    @Override
    @Transactional
    public void patchCommentContent(String uuid, DreamCommentRequest request) {
        User user = securityContextService.getPrincipal().getUser();

        Comment comment = commentRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new CommentNotFoundException("댓글을 찾을 수 없습니다"));

        if(!comment.getUser().equals(user)) {
            throw new ForbiddenUserException("해당 댓글을 수정할 수 없습니다");
        }

        comment.setContent(request.getContent());
    }

    @Override
    public NumberResponse queryCountOfComment(String uuid) {
        Dream dream = dreamRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new DreamNotFoundException("해당하는 꿈을 찾을 수 없습니다"));

        Integer count = commentRepository.countAllByDream(dream);

        return new NumberResponse(count);
    }

    @Override
    @Transactional
    public void checkDreamComment(String uuid) {
        User user = securityContextService.getPrincipal().getUser();

        Comment comment = commentRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new CommentNotFoundException("댓글을 찾을 수 없습니다"));

        if(comment.getUser().equals(user)) {
            throw new ForbiddenUserException("작성자가 아닙니다");
        }

        comment.setIsChecked(true);
    }
}
