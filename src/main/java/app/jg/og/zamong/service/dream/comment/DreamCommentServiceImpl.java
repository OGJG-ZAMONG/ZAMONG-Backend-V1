package app.jg.og.zamong.service.dream.comment;

import app.jg.og.zamong.dto.request.dream.DreamCommentRequest;
import app.jg.og.zamong.dto.response.DreamCommentResponse;
import app.jg.og.zamong.dto.response.UserInformationResponse;
import app.jg.og.zamong.entity.dream.Dream;
import app.jg.og.zamong.entity.dream.DreamRepository;
import app.jg.og.zamong.entity.dream.comment.Comment;
import app.jg.og.zamong.entity.dream.comment.CommentRepository;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.exception.business.CommentNotFoundException;
import app.jg.og.zamong.exception.business.DreamNotFoundException;
import app.jg.og.zamong.service.securitycontext.SecurityContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class DreamCommentServiceImpl implements DreamCommentService {

    private final DreamRepository dreamRepository;
    private final CommentRepository commentRepository;

    private final SecurityContextService securityContextService;

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
                .pComment(pComment)
                .user(user)
                .dream(dream)
                .build());

        return DreamCommentResponse.builder()
                .uuid(comment.getUuid())
                .content(comment.getContent())
                .user(UserInformationResponse.builder()
                        .uuid(user.getUuid())
                        .name(user.getName())
                        .profile(user.getProfile())
                        .build())
                .build();
    }
}
