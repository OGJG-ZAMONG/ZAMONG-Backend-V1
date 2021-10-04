package app.jg.og.zamong.service;

import app.jg.og.zamong.dto.request.CreateShareDreamRequest;
import app.jg.og.zamong.dto.response.CreateShareDreamResponse;
import app.jg.og.zamong.entity.dream.dreamtype.DreamTypeRepository;
import app.jg.og.zamong.entity.dream.enums.DreamType;
import app.jg.og.zamong.entity.dream.sharedream.ShareDream;
import app.jg.og.zamong.entity.dream.sharedream.ShareDreamRepository;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.entity.user.UserRepository;
import app.jg.og.zamong.service.dream.DreamServiceImpl;
import app.jg.og.zamong.service.securitycontext.SecurityContextService;
import app.jg.og.zamong.util.ShareDreamBuilder;
import app.jg.og.zamong.util.UserBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class DreamServiceTest {

    @InjectMocks
    private DreamServiceImpl dreamService;

    @Mock
    private SecurityContextService securityContextService;
    @Mock
    private ShareDreamRepository shareDreamRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private DreamTypeRepository dreamTypeRepository;

    @Test
    void 꿈_작성_성공() {
        //given
        User user = UserBuilder.build();
        String userId = user.getUuid().toString();
        ShareDream shareDream = ShareDreamBuilder.build(user);

        given(securityContextService.getName()).willReturn(userId);
        given(userRepository.findByUuid(user.getUuid())).willReturn(Optional.of(user));
        given(shareDreamRepository.save(any(ShareDream.class))).willReturn(shareDream);
        given(dreamTypeRepository.save(any())).willReturn(null);

        //when
        List<DreamType> dreamTypes = List.of(DreamType.LUCID_DREAM, DreamType.NIGHTMARE);
        CreateShareDreamRequest request = CreateShareDreamRequest.builder()
                .title(shareDream.getTitle())
                .content(shareDream.getContent())
                .quality(shareDream.getQuality())
                .dreamTypes(dreamTypes)
                .sleepDateTime(shareDream.getSleepDateTime())
                .sleepTime(shareDream.getSleepTime())
                .build();

        CreateShareDreamResponse response = dreamService.createShareDream(request);

        //when
        assertThat(response.getUuid()).isEqualTo(shareDream.getUuid());
        assertThat(response.getTitle()).isEqualTo(shareDream.getTitle());
    }
}