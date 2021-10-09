package app.jg.og.zamong.service;

import app.jg.og.zamong.dto.request.dream.DreamContentRequest;
import app.jg.og.zamong.dto.request.dream.DreamTitleRequest;
import app.jg.og.zamong.dto.request.dream.DreamTypesRequest;
import app.jg.og.zamong.dto.request.dream.sharedream.ShareDreamQualityRequest;
import app.jg.og.zamong.dto.request.dream.sharedream.ShareDreamRequest;
import app.jg.og.zamong.dto.request.dream.sharedream.ShareDreamSleepDateTimeRequest;
import app.jg.og.zamong.dto.response.ShareDreamResponse;
import app.jg.og.zamong.entity.dream.Dream;
import app.jg.og.zamong.entity.dream.DreamRepository;
import app.jg.og.zamong.entity.dream.dreamtype.DreamTypeRepository;
import app.jg.og.zamong.entity.dream.enums.DreamQuality;
import app.jg.og.zamong.entity.dream.enums.DreamType;
import app.jg.og.zamong.entity.dream.sharedream.ShareDream;
import app.jg.og.zamong.entity.dream.sharedream.ShareDreamRepository;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.entity.user.UserRepository;
import app.jg.og.zamong.service.dream.DreamServiceImpl;
import app.jg.og.zamong.service.securitycontext.SecurityContextService;
import app.jg.og.zamong.util.DreamBuilder;
import app.jg.og.zamong.util.ShareDreamBuilder;
import app.jg.og.zamong.util.UserBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

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

        ShareDreamResponse response = dreamService.createShareDream(request);

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
        dreamService.modifyShareDream(shareDream.getUuid().toString(), request);

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
        dreamService.patchShareDreamQuality(shareDream.getUuid().toString(), request);

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
        dreamService.patchShareDreamSleepDateTime(shareDream.getUuid().toString(), request);

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
        dreamService.patchDreamTitle(dream.getUuid().toString(), request);

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
        dreamService.patchDreamContent(dream.getUuid().toString(), request);

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
        dreamService.patchDreamTypes(dream.getUuid().toString(), request);

        Mockito.verify(dreamTypeRepository, Mockito.times(dreamTypes.size())).save(any());
    }
}
