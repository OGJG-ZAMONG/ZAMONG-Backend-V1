package app.jg.og.zamong.service.dream.interpretation.find;

import app.jg.og.zamong.dto.response.dream.interpretationdream.InterpretationDreamGroupResponse;
import app.jg.og.zamong.dto.response.dream.interpretationdream.InterpretationDreamInformationResponse;
import app.jg.og.zamong.dto.response.dream.interpretationdream.InterpretationDreamResponse;
import app.jg.og.zamong.entity.dream.dreamtype.DreamType;
import app.jg.og.zamong.entity.dream.interpretationdream.InterpretationDream;
import app.jg.og.zamong.entity.dream.interpretationdream.InterpretationDreamRepository;
import app.jg.og.zamong.exception.business.DreamNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InterpretationDreamFindServiceImpl implements InterpretationDreamFindService {

    private final InterpretationDreamRepository interpretationDreamRepository;

    @Override
    public InterpretationDreamGroupResponse queryInterpretationDreams(int page, int size) {
        Pageable request = PageRequest.of(page, size, Sort.by("updatedAt").descending());

        Page<InterpretationDream> interpretationDreamPage = interpretationDreamRepository.findAll(request);

        List<InterpretationDreamResponse> interpretationDreamResponses = interpretationDreamPage
                .map(this::interpretationDreamResponseOf)
                .toList();

        return InterpretationDreamGroupResponse.builder()
                .interpretationDreams(interpretationDreamResponses)
                .totalPage(interpretationDreamPage.getTotalPages())
                .totalSize(interpretationDreamPage.getTotalElements())
                .build();
    }

    @Override
    public InterpretationDreamGroupResponse searchInterpretationDreams(String title, String[] types) {
        List<app.jg.og.zamong.entity.dream.enums.DreamType> dreamTypes =
                Arrays.stream(types).map(app.jg.og.zamong.entity.dream.enums.DreamType::valueOf).collect(Collectors.toList());

        List<InterpretationDream> interpretationDreams = interpretationDreamRepository.findByTitleLikeAndDreamTypesIn(title, dreamTypes);

        List<InterpretationDreamResponse> interpretationDreamResponses = interpretationDreams
                .stream().map(this::interpretationDreamResponseOf)
                .collect(Collectors.toList());

        return InterpretationDreamGroupResponse.builder()
                .interpretationDreams(interpretationDreamResponses)
                .build();
    }

    private InterpretationDreamResponse interpretationDreamResponseOf(InterpretationDream interpretationDream) {
        return InterpretationDreamResponse.builder()
                .uuid(interpretationDream.getUuid())
                .defaultPostingImage(interpretationDream.getDefaultImage())
                .title(interpretationDream.getTitle())
                .dreamTypes(interpretationDream.getDreamTypes().stream().map(DreamType::getCode).collect(Collectors.toList()))
                .updatedAt(interpretationDream.getUpdatedAt())
                .user(InterpretationDreamResponse.User.builder()
                        .uuid(interpretationDream.getUser().getUuid())
                        .id(interpretationDream.getUser().getId())
                        .profile(interpretationDream.getUser().getProfile())
                        .build())
                .build();
    }

    @Override
    public InterpretationDreamInformationResponse queryInterpretationDreamResponse(String uuid) {
        InterpretationDream interpretationDream = interpretationDreamRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new DreamNotFoundException("해당하는 꿈을 찾을 수 없습니다"));

        return InterpretationDreamInformationResponse.builder()
                .uuid(interpretationDream.getUuid())
                .title(interpretationDream.getTitle())
                .content(interpretationDream.getContent())
                .updatedAt(interpretationDream.getUpdatedAt())
                .dreamTypes(interpretationDream.getDreamTypes().stream().map(DreamType::getCode).collect(Collectors.toList()))
                .attachmentImage(interpretationDream.getDefaultImage())
                .user(InterpretationDreamInformationResponse.User.builder()
                        .uuid(interpretationDream.getUser().getUuid())
                        .id(interpretationDream.getUser().getId())
                        .profile(interpretationDream.getUser().getProfile())
                        .build())
                .build();
    }
}
