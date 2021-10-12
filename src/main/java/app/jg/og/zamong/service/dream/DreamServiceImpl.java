package app.jg.og.zamong.service.dream;

import app.jg.og.zamong.dto.request.dream.DreamContentRequest;
import app.jg.og.zamong.dto.request.dream.DreamTitleRequest;
import app.jg.og.zamong.dto.request.dream.DreamTypesRequest;
import app.jg.og.zamong.dto.request.dream.sharedream.ShareDreamQualityRequest;
import app.jg.og.zamong.dto.request.dream.sharedream.ShareDreamRequest;
import app.jg.og.zamong.dto.request.dream.sharedream.ShareDreamSleepDateTimeRequest;
import app.jg.og.zamong.dto.response.CreateShareDreamResponse;
import app.jg.og.zamong.dto.response.ShareDreamGroupResponse;
import app.jg.og.zamong.dto.response.ShareDreamResponse;
import app.jg.og.zamong.entity.dream.Dream;
import app.jg.og.zamong.entity.dream.DreamRepository;
import app.jg.og.zamong.entity.dream.dreamtype.DreamType;
import app.jg.og.zamong.entity.dream.dreamtype.DreamTypeRepository;
import app.jg.og.zamong.entity.dream.sharedream.ShareDream;
import app.jg.og.zamong.entity.dream.sharedream.ShareDreamRepository;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.entity.user.UserRepository;
import app.jg.og.zamong.exception.business.DreamNotFoundException;
import app.jg.og.zamong.exception.business.UserNotFoundException;
import app.jg.og.zamong.service.securitycontext.SecurityContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DreamServiceImpl implements DreamService {

    private final SecurityContextService securityContextService;

    private final ShareDreamRepository shareDreamRepository;
    private final UserRepository userRepository;
    private final DreamTypeRepository dreamTypeRepository;
    private final DreamRepository dreamRepository;

    @Override
    @Transactional
    public CreateShareDreamResponse createShareDream(ShareDreamRequest request) {
        User user = userRepository.findByUuid(UUID.fromString(securityContextService.getName()))
                .orElseThrow(() -> new UserNotFoundException("해당하는 유저를 찾을 수 없습니다"));

        ShareDream shareDream = shareDreamRepository.save(ShareDream.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .user(user)
                .quality(request.getQuality())
                .isShared(false)
                .sleepDateTime(request.getSleepBeginDateTime())
                .sleepTime((int) ChronoUnit.HOURS.between(request.getSleepBeginDateTime(), request.getSleepEndDateTime()))
                .build()
        );

        request.getDreamTypes()
                .forEach(dt -> dreamTypeRepository.save(DreamType.builder()
                        .code(dt)
                        .dream(shareDream)
                        .build()));

        return CreateShareDreamResponse.builder()
                .uuid(shareDream.getUuid())
                .createdAt(shareDream.getCreatedAt())
                .updatedAt(shareDream.getUpdatedAt())
                .build();
    }

    @Override
    @Transactional
    public CreateShareDreamResponse modifyShareDream(String uuid, ShareDreamRequest request) {
        ShareDream shareDream = shareDreamRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new DreamNotFoundException("해당하는 꿈을 찾을 수 없습니다"));

        shareDream.setTitle(request.getTitle());
        shareDream.setContent(request.getContent());
        shareDream.setQuality(request.getQuality());
        shareDream.setSleepDateTime(request.getSleepBeginDateTime());
        shareDream.setSleepTime((int) ChronoUnit.HOURS.between(request.getSleepBeginDateTime(), request.getSleepEndDateTime()));

        dreamTypeRepository.deleteByDream(shareDream);

        request.getDreamTypes()
                .forEach((dt -> dreamTypeRepository.save(DreamType.builder()
                        .dream(shareDream)
                        .code(dt)
                        .build())));

        return CreateShareDreamResponse.builder()
                .uuid(shareDream.getUuid())
                .createdAt(shareDream.getCreatedAt())
                .updatedAt(shareDream.getUpdatedAt())
                .build();
    }

    @Override
    public void patchShareDreamQuality(String uuid, ShareDreamQualityRequest request) {
        ShareDream shareDream = shareDreamRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new DreamNotFoundException("해당하는 꿈을 찾을 수 없습니다"));

        shareDream.setQuality(request.getQuality());
    }

    @Override
    public void patchShareDreamSleepDateTime(String uuid, ShareDreamSleepDateTimeRequest request) {
        ShareDream shareDream = shareDreamRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new DreamNotFoundException("해당하는 꿈을 찾을 수 없습니다"));

        shareDream.setSleepDateTime(request.getSleepBeginDateTime());
        shareDream.setSleepTime((int) ChronoUnit.HOURS.between(request.getSleepBeginDateTime(), request.getSleepEndDateTime()));
    }

    @Override
    public void patchDreamTitle(String uuid, DreamTitleRequest request) {
        Dream dream = dreamRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new DreamNotFoundException("해당하는 꿈을 찾을 수 없습니다"));

        dream.setTitle(request.getTitle());
    }

    @Override
    public void patchDreamContent(String uuid, DreamContentRequest request) {
        Dream dream = dreamRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new DreamNotFoundException("해당하는 꿈을 찾을 수 없습니다"));

        dream.setContent(request.getContent());
    }

    @Override
    @Transactional
    public void patchDreamTypes(String uuid, DreamTypesRequest request) {
        Dream dream = dreamRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new DreamNotFoundException("해당하는 꿈을 찾을 수 없습니다"));

        dreamTypeRepository.deleteByDream(dream);

        request.getDreamTypes()
                .forEach((dt -> dreamTypeRepository.save(DreamType.builder()
                        .dream(dream)
                        .code(dt)
                        .build())));
    }

    @Override
    public ShareDreamGroupResponse queryShareDreams(int page, int size) {
        Pageable request = PageRequest.of(page, size, Sort.by("shareDateTime").descending());
        Page<ShareDream> shareDreamPage = shareDreamRepository.findByIsSharedIsTrue(request);

        List<ShareDreamResponse> shareDreamGroup = shareDreamPage.map(sd -> ShareDreamResponse.builder()
                .uuid(sd.getUuid())
                .title(sd.getTitle())
                .profile(sd.getUser().getProfile())
                .isShared(sd.getIsShared())
                .lucyCount(sd.getLucyCount())
                .dreamTypes(sd.getDreamTypes()
                        .stream()
                        .map(DreamType::getCode)
                        .collect(Collectors.toList()))
                .defaultPostingImage(sd.getDefaultImage())
                .build()).toList();

        return ShareDreamGroupResponse.builder()
                .shareDreams(shareDreamGroup)
                .totalPage(shareDreamPage.getTotalPages())
                .totalSize(shareDreamPage.getTotalElements())
                .build();
    }
}
