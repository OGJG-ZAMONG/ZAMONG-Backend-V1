package app.jg.og.zamong.service.dream;

import app.jg.og.zamong.dto.request.dream.DreamContentRequest;
import app.jg.og.zamong.dto.request.dream.DreamTitleRequest;
import app.jg.og.zamong.dto.request.dream.DreamTypesRequest;
import app.jg.og.zamong.entity.dream.Dream;
import app.jg.og.zamong.entity.dream.DreamRepository;
import app.jg.og.zamong.entity.dream.dreamtype.DreamTypeRepository;
import app.jg.og.zamong.entity.dream.enums.DreamType;
import app.jg.og.zamong.service.UnitTest;
import app.jg.og.zamong.util.DreamBuilder;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

public class DreamServiceTest extends UnitTest {

    @InjectMocks
    private DreamServiceImpl dreamService;

    @Mock
    private DreamTypeRepository dreamTypeRepository;
    @Mock
    private DreamRepository dreamRepository;

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

        verify(dreamTypeRepository, times(dreamTypes.size())).save(any());
    }
}
