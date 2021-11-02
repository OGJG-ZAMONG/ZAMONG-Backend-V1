package app.jg.og.zamong.service.dream;

import app.jg.og.zamong.dto.request.dream.sharedream.*;
import app.jg.og.zamong.dto.response.CreateDreamResponse;
import app.jg.og.zamong.dto.response.DoShareDreamResponse;
import app.jg.og.zamong.entity.dream.attachment.AttachmentImage;
import app.jg.og.zamong.entity.dream.attachment.AttachmentImageRepository;
import app.jg.og.zamong.entity.dream.dreamtype.DreamTypeRepository;
import app.jg.og.zamong.entity.dream.enums.*;
import app.jg.og.zamong.entity.dream.sharedream.*;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.entity.user.UserRepository;
import app.jg.og.zamong.service.UnitTest;
import app.jg.og.zamong.service.dream.share.ShareDreamServiceImpl;
import app.jg.og.zamong.service.file.FileSaveService;
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
    private FileSaveService fileSaveService;
    @Mock
    private ShareDreamRepository shareDreamRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private DreamTypeRepository dreamTypeRepository;
    @Mock
    private AttachmentImageRepository attachmentImageRepository;

    @Test
    void 꿈_작성_성공() {
        //given
        User user = UserBuilder.build();
        String userId = user.getUuid().toString();
        ShareDream shareDream = ShareDreamBuilder.build(user);

        given(securityContextService.getName()).willReturn(userId);
        given(userRepository.findById(user.getUuid())).willReturn(Optional.of(user));
        given(shareDreamRepository.save(any(ShareDream.class))).willReturn(shareDream);
        given(dreamTypeRepository.save(any())).willReturn(null);
        given(attachmentImageRepository.save(any())).willReturn(null);

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

        CreateDreamResponse response = shareDreamService.createShareDream(request);

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
    void 공유꿈_사진_수정_성공() {
        //given
        ShareDream shareDream = ShareDreamBuilder.build(null);

        String uploadHost = "uploadHost";
        String uploadPath = "uploadPath";

        given(shareDreamRepository.findById(shareDream.getUuid())).willReturn(Optional.of(shareDream));
        given(fileSaveService.queryHostName()).willReturn(uploadHost);
        given(fileSaveService.saveFile(any(), any())).willReturn(uploadPath);

        AttachmentImage attachmentImage = shareDream.getAttachmentImages().get(0);

        //when
        shareDreamService.patchShareDreamImage(shareDream.getUuid().toString(), null);

        //then
        assertThat(attachmentImage.getHost()).isEqualTo(uploadHost);
        assertThat(attachmentImage.getPath()).isEqualTo(uploadPath);
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
    void 꿈_공유_성공() {
        //given
        ShareDream shareDream = ShareDreamBuilder.build(null);
        LocalDateTime beforeShareDateTime = LocalDateTime.now().minusSeconds(3);

        given(shareDreamRepository.findById(shareDream.getUuid())).willReturn(Optional.of(shareDream));

        //when
        DoShareDreamResponse response = shareDreamService.doShareDream(shareDream.getUuid().toString());

        //then
        assertThat(shareDream.getIsShared()).isTrue();
        assertThat(response.getShareDateTime()).isAfter(beforeShareDateTime);
    }
}
