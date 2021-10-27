package app.jg.og.zamong.service.dream;

import app.jg.og.zamong.dto.request.dream.DreamCommentRequest;
import app.jg.og.zamong.dto.response.DreamCommentResponse;
import app.jg.og.zamong.entity.dream.Dream;
import app.jg.og.zamong.entity.dream.DreamRepository;
import app.jg.og.zamong.entity.dream.comment.Comment;
import app.jg.og.zamong.entity.dream.comment.CommentRepository;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.security.auth.AuthenticationDetails;
import app.jg.og.zamong.service.UnitTest;
import app.jg.og.zamong.service.dream.comment.DreamCommentServiceImpl;
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

public class DreamCommentServcieTest extends UnitTest {

    @InjectMocks
    private DreamCommentServiceImpl dreamCommentService;

    @Mock
    private DreamRepository dreamRepository;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private SecurityContextService securityContextService;

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
        assertThat(response.getContent()).isEqualTo(content);
    }
}
