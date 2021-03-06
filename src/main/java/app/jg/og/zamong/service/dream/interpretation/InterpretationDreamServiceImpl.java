package app.jg.og.zamong.service.dream.interpretation;

import app.jg.og.zamong.dto.request.dream.interpretationdream.InterpretationDreamRequest;
import app.jg.og.zamong.dto.request.dream.interpretationdream.SelectInterpretationDreamRequest;
import app.jg.og.zamong.dto.response.dream.CreateDreamResponse;
import app.jg.og.zamong.dto.response.Response;
import app.jg.og.zamong.dto.response.StringResponse;
import app.jg.og.zamong.dto.response.dream.interpretationdream.InterpretationDreamCategoryResponse;
import app.jg.og.zamong.entity.dream.attachment.AttachmentImage;
import app.jg.og.zamong.entity.dream.attachment.AttachmentImageRepository;
import app.jg.og.zamong.entity.dream.comment.Comment;
import app.jg.og.zamong.entity.dream.comment.CommentRepository;
import app.jg.og.zamong.entity.dream.dreamtype.DreamType;
import app.jg.og.zamong.entity.dream.dreamtype.DreamTypeRepository;
import app.jg.og.zamong.entity.dream.enums.DreamTag;
import app.jg.og.zamong.entity.dream.interpretationdream.InterpretationDream;
import app.jg.og.zamong.entity.dream.interpretationdream.InterpretationDreamRepository;
import app.jg.og.zamong.entity.dream.interpretationdream.interpretation.Interpretation;
import app.jg.og.zamong.entity.dream.interpretationdream.interpretation.InterpretationRepository;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.entity.user.UserRepository;
import app.jg.og.zamong.exception.business.CommentNotFoundException;
import app.jg.og.zamong.exception.business.DreamNotFoundException;
import app.jg.og.zamong.exception.business.ForbiddenUserException;
import app.jg.og.zamong.exception.business.UserNotFoundException;
import app.jg.og.zamong.service.file.FileSaveService;
import app.jg.og.zamong.service.securitycontext.SecurityContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    private final SecurityContextService securityContextService;
    private final FileSaveService fileSaveService;

    @Override
    @Transactional
    public CreateDreamResponse createInterpretationDream(InterpretationDreamRequest request) {
        User user = userRepository.save(securityContextService.getPrincipal().getUser());

        InterpretationDream interpretationDream = interpretationDreamRepository.save(InterpretationDream.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .user(user)
                .lucyCount(request.getLucyCount())
                .build());

        request.getDreamTypes()
                .forEach(dt -> dreamTypeRepository.save(DreamType.builder()
                        .code(dt)
                        .dream(interpretationDream)
                        .build()));

        attachmentImageRepository.save(AttachmentImage
                .builder()
                .tag(DreamTag.INTERPRETATION_DREAM)
                .host(fileSaveService.queryHostName())
                .path(FileSaveService.DEFAULT_IMAGES[(int)(System.currentTimeMillis() % 5)])
                .dream(interpretationDream)
                .build());

        return CreateDreamResponse.builder()
                .uuid(interpretationDream.getUuid())
                .createdAt(interpretationDream.getCreatedAt())
                .updatedAt(interpretationDream.getUpdatedAt())
                .build();
    }

    @Override
    @Transactional
    public CreateDreamResponse modifyInterpretationDream(String uuid, InterpretationDreamRequest request) {
        User user = userRepository.save(securityContextService.getPrincipal().getUser());

        InterpretationDream interpretationDream = interpretationDreamRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new DreamNotFoundException("???????????? ?????? ?????? ??? ????????????"));

        user.increaseLucy(interpretationDream.getLucyCount());
        user.decreaseLucy(request.getLucyCount());

        interpretationDream.setTitle(request.getTitle());
        interpretationDream.setContent(request.getContent());
        interpretationDream.setLucyCount(request.getLucyCount());

        dreamTypeRepository.deleteByDream(interpretationDream);

        request.getDreamTypes()
                .forEach((dt -> dreamTypeRepository.save(DreamType.builder()
                        .dream(interpretationDream)
                        .code(dt)
                        .build())));

        return CreateDreamResponse.builder()
                .uuid(interpretationDream.getUuid())
                .createdAt(interpretationDream.getCreatedAt())
                .updatedAt(interpretationDream.getUpdatedAt())
                .build();
    }

    @Override
    @Transactional
    public Response selectInterpretationDream(SelectInterpretationDreamRequest request) {
        User user = userRepository.save(securityContextService.getPrincipal().getUser());

        InterpretationDream interpretationDream = interpretationDreamRepository.findById(request.getDreamUuid())
                .orElseThrow(() -> new DreamNotFoundException("???????????? ?????? ?????? ??? ????????????"));

        Comment comment = commentRepository.findById(request.getCommentUuid())
                .orElseThrow(() -> new CommentNotFoundException("????????? ?????? ??? ????????????"));

        if(!interpretationDream.getUser().equals(user) || !comment.getDream().equals(interpretationDream)) {
            throw new ForbiddenUserException("???????????? ?????? ????????????");
        }

        user.decreaseLucy(interpretationDream.getLucyCount());
        comment.getUser().increaseLucy(interpretationDream.getLucyCount());

        comment.setIsSelected(true);
        interpretationDream.setIsInterpretation(true);

        return new StringResponse(interpretationDream.getLucyCount() + "????????? ?????????????????????");
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
                .orElseThrow(() -> new DreamNotFoundException("????????? ?????? ??? ????????????"));

        return new StringResponse(interpretation.getInterpretation());
    }
}
