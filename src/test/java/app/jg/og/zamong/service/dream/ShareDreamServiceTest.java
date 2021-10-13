package app.jg.og.zamong.service.dream;

import app.jg.og.zamong.dto.request.dream.*;
import app.jg.og.zamong.dto.request.dream.sharedream.*;
import app.jg.og.zamong.dto.response.CreateShareDreamResponse;
import app.jg.og.zamong.entity.dream.*;
import app.jg.og.zamong.entity.dream.dreamtype.DreamTypeRepository;
import app.jg.og.zamong.entity.dream.enums.*;
import app.jg.og.zamong.entity.dream.sharedream.*;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.entity.user.UserRepository;
import app.jg.og.zamong.service.UnitTest;
import app.jg.og.zamong.service.securitycontext.SecurityContextService;
import app.jg.og.zamong.util.*;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

public class ShareDreamServiceTest extends UnitTest {

    @InjectMocks
    private ShareDreamServiceImpl shareDreamService;

    @Mock
    private SecurityContextService securityContextService;
    @Mock
    private ShareDreamRepository shareDreamRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private DreamTypeRepository dreamTypeRepository;
    @Mock
    private DreamRepository dreamRepository;

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
        ShareDreamRequest request = ShareDreamRequest.builder()
                .title(shareDream.getTitle())
                .content(shareDream.getContent())
                .quality(shareDream.getQuality())
                .dreamTypes(dreamTypes)
                .sleepBeginDateTime(LocalDateTime.now())
                .sleepEndDateTime(LocalDateTime.now())
                .build();

        CreateShareDreamResponse response = shareDreamService.createShareDream(request);

        //when
        assertThat(response.getUuid()).isEqualTo(shareDream.getUuid());
    }

    @Test
    void 꿈_수정_성공() {
        //given
        ShareDream shareDream = ShareDreamBuilder.build(null);

        given(shareDreamRepository.findById(shareDream.getUuid())).willReturn(Optional.of(shareDream));
        willDoNothing().given(dreamTypeRepository).deleteByDream(shareDream);

        //when
        String patchedTitle = "patchedTitle";
        String patchedContent = "patchedContent";
        DreamQuality pathedQuality = DreamQuality.WORST;

        ShareDreamRequest request = ShareDreamRequest.builder()
                .title(patchedTitle)
                .content(patchedContent)
                .quality(pathedQuality)
                .dreamTypes(Collections.emptyList())
                .sleepBeginDateTime(LocalDateTime.now())
                .sleepEndDateTime(LocalDateTime.now())
                .build();
        shareDreamService.modifyShareDream(shareDream.getUuid().toString(), request);

        //then
        assertThat(shareDream.getTitle()).isEqualTo(patchedTitle);
        assertThat(shareDream.getContent()).isEqualTo(patchedContent);
        assertThat(shareDream.getQuality()).isEqualTo(pathedQuality);
    }

    @Test
    void 공유꿈_품질_수정_성공() {
        //given
        ShareDream shareDream = ShareDreamBuilder.build(null);

        given(shareDreamRepository.findById(shareDream.getUuid())).willReturn(Optional.of(shareDream));

        //when
        DreamQuality pathedQuality = DreamQuality.WORST;

        ShareDreamQualityRequest request = ShareDreamQualityRequest.builder()
                .quality(pathedQuality)
                .build();
        shareDreamService.patchShareDreamQuality(shareDream.getUuid().toString(), request);

        //then
        assertThat(shareDream.getQuality()).isEqualTo(pathedQuality);
    }

    @Test
    void 공유꿈_꾼시각_수정_성공() {
        //given
        int beginDateTimeHour = 2;
        int endDateTimeHour = 12;
        LocalDateTime beginDateTime = LocalDateTime.of(2021, 10, 7, beginDateTimeHour, 37);
        LocalDateTime endDateTime = LocalDateTime.of(2021, 10, 7, endDateTimeHour, 37);

        ShareDream shareDream = ShareDreamBuilder.build(null);
        given(shareDreamRepository.findById(shareDream.getUuid())).willReturn(Optional.of(shareDream));

        //when
        ShareDreamSleepDateTimeRequest request = ShareDreamSleepDateTimeRequest.builder()
                .sleepBeginDateTime(beginDateTime)
                .sleepEndDateTime(endDateTime)
                .build();
        shareDreamService.patchShareDreamSleepDateTime(shareDream.getUuid().toString(), request);

        //then
        assertThat(shareDream.getSleepDateTime()).isEqualTo(beginDateTime);
        assertThat(shareDream.getSleepTime()).isEqualTo(endDateTimeHour - beginDateTimeHour);
    }

    @Test
    void 꿈_제목_수정_성공() {
        //given
        Dream dream = DreamBuilder.build();
        given(dreamRepository.findById(dream.getUuid())).willReturn(Optional.of(dream));

        //when
        String patchedTitle = "patchedTitle";
        DreamTitleRequest request = DreamTitleRequest.builder()
                .title(patchedTitle)
                .build();
        shareDreamService.patchDreamTitle(dream.getUuid().toString(), request);

        assertThat(dream.getTitle()).isEqualTo(patchedTitle);
    }

    @Test
    void 꿈_내용_수정_성공() {
        //given
        Dream dream = DreamBuilder.build();
        given(dreamRepository.findById(dream.getUuid())).willReturn(Optional.of(dream));

        //when
        String patchedContent = "patchedContent";
        DreamContentRequest request = DreamContentRequest.builder()
                .content(patchedContent)
                .build();
        shareDreamService.patchDreamContent(dream.getUuid().toString(), request);

        assertThat(dream.getContent()).isEqualTo(patchedContent);
    }

    @Test
    void 꿈_유형_수정_성공() {
        //given
        Dream dream = DreamBuilder.build();
        given(dreamRepository.findById(dream.getUuid())).willReturn(Optional.of(dream));
        willDoNothing().given(dreamTypeRepository).deleteByDream(dream);
        given(dreamTypeRepository.save(any())).willReturn(null);

        //when
        DreamType patchedDreamType1 = DreamType.LUCID_DREAM;
        DreamType patchedDreamType2 = DreamType.NIGHTMARE;
        List<DreamType> dreamTypes = List.of(patchedDreamType1, patchedDreamType2);

        DreamTypesRequest request = DreamTypesRequest.builder()
                .dreamTypes(dreamTypes)
                .build();
        shareDreamService.patchDreamTypes(dream.getUuid().toString(), request);

        Mockito.verify(dreamTypeRepository, Mockito.times(dreamTypes.size())).save(any());
    }
}
