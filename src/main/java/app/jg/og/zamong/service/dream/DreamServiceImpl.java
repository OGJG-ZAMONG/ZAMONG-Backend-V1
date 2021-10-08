package app.jg.og.zamong.service.dream;

import app.jg.og.zamong.dto.request.dream.sharedream.ShareDreamQualityRequest;
import app.jg.og.zamong.dto.request.dream.sharedream.ShareDreamRequest;
import app.jg.og.zamong.dto.response.ShareDreamResponse;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class DreamServiceImpl implements DreamService {

    private final SecurityContextService securityContextService;

    private final ShareDreamRepository shareDreamRepository;
    private final UserRepository userRepository;
    private final DreamTypeRepository dreamTypeRepository;

    @Override
    @Transactional
    public ShareDreamResponse createShareDream(ShareDreamRequest request) {
        User user = userRepository.findByUuid(UUID.fromString(securityContextService.getName()))
                .orElseThrow(() -> new UserNotFoundException("해당하는 유저를 찾을 수 없습니다"));

        ShareDream shareDream = shareDreamRepository.save(ShareDream.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .user(user)
                .quality(request.getQuality())
                .isShared(false)
                .sleepDateTime(request.getSleepBeginDatetime())
                .sleepTime((int) ChronoUnit.HOURS.between(request.getSleepBeginDatetime(), request.getSleepEndDatetime()))
                .build()
        );

        request.getDreamTypes()
                .forEach(dt -> dreamTypeRepository.save(DreamType.builder()
                    .code(dt)
                    .dream(shareDream)
                    .build()));

        return ShareDreamResponse.builder()
                .uuid(shareDream.getUuid())
                .createdAt(shareDream.getCreatedAt())
                .updatedAt(shareDream.getUpdatedAt())
                .build();
    }

    @Override
    @Transactional
    public ShareDreamResponse modifyShareDream(String uuid, ShareDreamRequest request) {
        ShareDream shareDream = shareDreamRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new DreamNotFoundException("해당하는 꿈을 찾을 수 없습니다"));

        shareDream.setTitle(request.getTitle());
        shareDream.setContent(request.getContent());
        shareDream.setQuality(request.getQuality());
        shareDream.setSleepDateTime(request.getSleepBeginDatetime());
        shareDream.setSleepTime((int) ChronoUnit.HOURS.between(request.getSleepBeginDatetime(), request.getSleepEndDatetime()));

        dreamTypeRepository.deleteByDream(shareDream);

        request.getDreamTypes()
                .forEach((dt -> dreamTypeRepository.save(DreamType.builder()
                        .dream(shareDream)
                        .code(dt)
                        .build())));

        return ShareDreamResponse.builder()
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
}
