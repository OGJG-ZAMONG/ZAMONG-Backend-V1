package app.jg.og.zamong.service.dream.interpretation;

import app.jg.og.zamong.dto.request.dream.interpretationdream.InterpretationDreamRequest;
import app.jg.og.zamong.dto.response.CreateDreamResponse;
import app.jg.og.zamong.dto.response.Response;
import app.jg.og.zamong.dto.response.StringResponse;
import app.jg.og.zamong.dto.response.dream.interpretationdream.InterpretationDreamCategoryResponse;
import app.jg.og.zamong.entity.dream.attachment.AttachmentImage;
import app.jg.og.zamong.entity.dream.attachment.AttachmentImageRepository;
import app.jg.og.zamong.entity.dream.dreamtype.DreamType;
import app.jg.og.zamong.entity.dream.dreamtype.DreamTypeRepository;
import app.jg.og.zamong.entity.dream.enums.DreamTag;
import app.jg.og.zamong.entity.dream.interpretationdream.InterpretationDream;
import app.jg.og.zamong.entity.dream.interpretationdream.InterpretationDreamRepository;
import app.jg.og.zamong.entity.dream.interpretationdream.interpretation.Interpretation;
import app.jg.og.zamong.entity.dream.interpretationdream.interpretation.InterpretationRepository;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.exception.business.DreamNotFoundException;
import app.jg.og.zamong.service.securitycontext.SecurityContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InterpretationDreamServiceImpl implements InterpretationDreamService {

    private final InterpretationRepository interpretationRepository;
    private final InterpretationDreamRepository interpretationDreamRepository;
    private final DreamTypeRepository dreamTypeRepository;
    private final AttachmentImageRepository attachmentImageRepository;

    private final SecurityContextService securityContextService;

    @Override
    public CreateDreamResponse createInterpretationDream(InterpretationDreamRequest request) {
        User user = securityContextService.getPrincipal().getUser();

        InterpretationDream interpretationDream = interpretationDreamRepository.save(InterpretationDream.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .user(user)
                .build());

        request.getDreamTypes()
                .forEach(dt -> dreamTypeRepository.save(DreamType.builder()
                        .code(dt)
                        .dream(interpretationDream)
                        .build()));

        attachmentImageRepository.save(AttachmentImage
                .builder()
                .tag(DreamTag.INTERPRETATION_DREAM)
                .dream(interpretationDream)
                .build());

        return CreateDreamResponse.builder()
                .uuid(interpretationDream.getUuid())
                .createdAt(interpretationDream.getCreatedAt())
                .updatedAt(interpretationDream.getUpdatedAt())
                .build();
    }

    @Override
    public InterpretationDreamCategoryResponse queryInterpretationDreamCategory() {
        List<Interpretation> interpretations = interpretationRepository.findAll();

        List<InterpretationDreamCategoryResponse.InterpretationDream> interpretationDreams = interpretations.stream()
                .map(id -> InterpretationDreamCategoryResponse.InterpretationDream.builder()
                        .uuid(id.getUuid())
                        .dreamName(id.getDreamName())
                        .build())
                .collect(Collectors.toList());

        return InterpretationDreamCategoryResponse.builder()
                .interpretationDreams(interpretationDreams)
                .build();
    }

    @Override
    public Response queryInterpretation(String uuid) {
        Interpretation interpretation = interpretationRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new DreamNotFoundException("해몽을 찾을 수 없습니다"));

        return new StringResponse(interpretation.getInterpretation());
    }
}
