package app.jg.og.zamong.service.dream;

import app.jg.og.zamong.dto.request.dream.DreamCommentRequest;
import app.jg.og.zamong.dto.response.dream.comment.DreamCommendGroupResponse;
import app.jg.og.zamong.dto.response.dream.comment.DreamCommentResponse;
import app.jg.og.zamong.entity.dream.Dream;
import app.jg.og.zamong.entity.dream.DreamRepository;
import app.jg.og.zamong.entity.dream.comment.Comment;
import app.jg.og.zamong.entity.dream.comment.CommentRepository;
import app.jg.og.zamong.entity.dream.comment.recommend.RecommendRepository;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.exception.business.BusinessException;
import app.jg.og.zamong.exception.business.CommentNotFoundException;
import app.jg.og.zamong.exception.business.DreamNotFoundException;
import app.jg.og.zamong.security.auth.AuthenticationDetails;
import app.jg.og.zamong.service.UnitTest;
import app.jg.og.zamong.service.dream.comment.DreamCommentServiceImpl;
import app.jg.og.zamong.service.dream.comment.filtering.DreamCommentFilteringService;
import app.jg.og.zamong.service.securitycontext.SecurityContextService;
import app.jg.og.zamong.util.DreamBuilder;
import app.jg.og.zamong.util.UserBuilder;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

public class DreamCommentServcieTest extends UnitTest {

    @InjectMocks
    private DreamCommentServiceImpl dreamCommentService;

    @Mock
    private DreamRepository dreamRepository;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private SecurityContextService securityContextService;
    @Mock
    private RecommendRepository recommendRepository;
    @Mock
    private DreamCommentFilteringService dreamCommentFilteringService;

    @Test
    void 댓글작성_실패() {
        //given
        User user = UserBuilder.build();
        Dream dream = DreamBuilder.build();

        String pCommentId = UUID.randomUUID().toString();
        String content = "comment content";

        given(securityContextService.getPrincipal()).willReturn(new AuthenticationDetails(user));
        given(dreamRepository.findById(dream.getUuid())).willReturn(Optional.of(dream));
        given(commentRepository.findById(UUID.fromString(pCommentId))).willReturn(Optional.empty());

        //when
        DreamCommentRequest request = DreamCommentRequest.builder()
                .content(content)
                .pComment(pCommentId)
                .build();

        try {
            dreamCommentService.createDream(dream.getUuid().toString(), request);
        } catch (BusinessException e) {
            assertThat(e).isInstanceOf(CommentNotFoundException.class);
        }

        try {
            String invalidDreamId = UUID.randomUUID().toString();
            dreamCommentService.createDream(invalidDreamId, request);
        } catch (BusinessException e) {
            assertThat(e).isInstanceOf(DreamNotFoundException.class);
        }
    }

    @Test
    void 댓글작성_성공() {
        //given
        User user = UserBuilder.build();
        Dream dream = DreamBuilder.build();
        String content = "comment content";

        given(securityContextService.getPrincipal()).willReturn(new AuthenticationDetails(user));
        given(dreamRepository.findById(dream.getUuid())).willReturn(Optional.of(dream));
        given(commentRepository.save(any())).willReturn(Comment.builder()
                .uuid(UUID.randomUUID())
                .content(content)
                .build());

        //when
        DreamCommentRequest request = DreamCommentRequest.builder()
                .content(content)
                .pComment(null)
                .build();
        DreamCommentResponse response = dreamCommentService.createDream(dream.getUuid().toString(), request);

        //then
        assertThat(response.getDepth()).isEqualTo(0);
    }

    @Test
    void 대댓글작성_성공() {
        //given
        User user = UserBuilder.build();
        Dream dream = DreamBuilder.build();
        String content = "comment content";
        int commentDepth = 10;
        String pCommentId = UUID.randomUUID().toString();

        given(securityContextService.getPrincipal()).willReturn(new AuthenticationDetails(user));
        given(dreamRepository.findById(dream.getUuid())).willReturn(Optional.of(dream));
        given(commentRepository.findById(UUID.fromString(pCommentId))).willReturn(Optional.of(Comment.builder()
                .uuid(UUID.randomUUID())
                .content(content)
                .depth(commentDepth)
                .build()));
        given(commentRepository.save(any())).willReturn(Comment.builder()
                .uuid(UUID.randomUUID())
                .content(content)
                .build());

        //when
        DreamCommentRequest request = DreamCommentRequest.builder()
                .content(content)
                .pComment(pCommentId)
                .build();
        DreamCommentResponse response = dreamCommentService.createDream(dream.getUuid().toString(), request);

        //then
        assertThat(response.getDepth()).isEqualTo(commentDepth + 1);
    }

    @Test
    void 댓글목록조회_성공() {
        //given
        User user = UserBuilder.build();
        Dream dream = DreamBuilder.build();

        given(securityContextService.getPrincipal()).willReturn(new AuthenticationDetails(user));
        given(dreamRepository.findById(dream.getUuid())).willReturn(Optional.of(dream));

        //when
        DreamCommendGroupResponse response = dreamCommentService.queryDreamComment(dream.getUuid().toString());

        assertThat(response.getComments().size()).isEqualTo(dream.getComments().stream()
                .filter(comment -> comment.getDepth() == 0)
                .count());
    }
}
