package app.jg.og.zamong.service.dream.share;

import app.jg.og.zamong.dto.request.dream.sharedream.ShareDreamQualityRequest;
import app.jg.og.zamong.dto.request.dream.sharedream.ShareDreamRequest;
import app.jg.og.zamong.dto.request.dream.sharedream.ShareDreamSleepDateTimeRequest;
import app.jg.og.zamong.dto.response.dream.CreateDreamResponse;
import app.jg.og.zamong.dto.response.dream.sharedream.DoShareDreamResponse;
import app.jg.og.zamong.entity.dream.attachment.AttachmentImage;
import app.jg.og.zamong.entity.dream.attachment.AttachmentImageRepository;
import app.jg.og.zamong.entity.dream.dreamtype.DreamType;
import app.jg.og.zamong.entity.dream.dreamtype.DreamTypeRepository;
import app.jg.og.zamong.entity.dream.enums.DreamTag;
import app.jg.og.zamong.entity.dream.sharedream.ShareDream;
import app.jg.og.zamong.entity.dream.sharedream.ShareDreamRepository;
import app.jg.og.zamong.entity.dream.sharedream.lucypoint.ShareDreamLucyPoint;
import app.jg.og.zamong.entity.dream.sharedream.lucypoint.ShareDreamLucyPointRepository;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.entity.user.UserRepository;
import app.jg.og.zamong.exception.business.DreamNotFoundException;
import app.jg.og.zamong.exception.business.ForbiddenUserException;
import app.jg.og.zamong.exception.business.UserNotFoundException;
import app.jg.og.zamong.service.file.FileSaveService;
import app.jg.og.zamong.service.securitycontext.SecurityContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShareDreamServiceImpl implements ShareDreamService {

    private final SecurityContextService securityContextService;
    private final FileSaveService fileSaveService;

    private final ShareDreamRepository shareDreamRepository;
    private final UserRepository userRepository;
    private final DreamTypeRepository dreamTypeRepository;
    private final AttachmentImageRepository attachmentImageRepository;
    private final ShareDreamLucyPointRepository shareDreamLucyPointRepository;

    @Override
    @Transactional
    public CreateDreamResponse createShareDream(ShareDreamRequest request) {
        User user = userRepository.findById(UUID.fromString(securityContextService.getName()))
                .orElseThrow(() -> new UserNotFoundException("???????????? ????????? ?????? ??? ????????????"));

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

        attachmentImageRepository.save(AttachmentImage
                .builder()
                .tag(DreamTag.SHARE_DREAM)
                .host(fileSaveService.queryHostName())
                .path(FileSaveService.DEFAULT_IMAGES[(int)(System.currentTimeMillis() % 5)])
                .dream(shareDream)
                .build());

        return CreateDreamResponse.builder()
                .uuid(shareDream.getUuid())
                .createdAt(shareDream.getCreatedAt())
                .updatedAt(shareDream.getUpdatedAt())
                .build();
    }

    @Override
    @Transactional
    public CreateDreamResponse modifyShareDream(String uuid, ShareDreamRequest request) {
        ShareDream shareDream = shareDreamRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new DreamNotFoundException("???????????? ?????? ?????? ??? ????????????"));

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

        return CreateDreamResponse.builder()
                .uuid(shareDream.getUuid())
                .createdAt(shareDream.getCreatedAt())
                .updatedAt(shareDream.getUpdatedAt())
                .build();
    }

    @Override
    @Transactional
    public void patchShareDreamImage(String uuid, MultipartFile file) {
        ShareDream shareDream = shareDreamRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new DreamNotFoundException("???????????? ?????? ?????? ??? ????????????"));

        String host = fileSaveService.queryHostName();
        String path = fileSaveService.saveFile(file, "dream/share");

        AttachmentImage attachmentImage = shareDream.getAttachmentImages().get(0);

        attachmentImage.setHost(host);
        attachmentImage.setPath(path);
    }

    @Override
    @Transactional
    public void patchShareDreamQuality(String uuid, ShareDreamQualityRequest request) {
        ShareDream shareDream = shareDreamRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new DreamNotFoundException("???????????? ?????? ?????? ??? ????????????"));

        shareDream.setQuality(request.getQuality());
    }

    @Override
    @Transactional
    public void patchShareDreamSleepDateTime(String uuid, ShareDreamSleepDateTimeRequest request) {
        ShareDream shareDream = shareDreamRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new DreamNotFoundException("???????????? ?????? ?????? ??? ????????????"));

        shareDream.setSleepDateTime(request.getSleepBeginDateTime());
        shareDream.setSleepTime((int) ChronoUnit.HOURS.between(request.getSleepBeginDateTime(), request.getSleepEndDateTime()));
    }

    @Override
    @Transactional
    public DoShareDreamResponse doShareDream(String uuid) {
        ShareDream shareDream = shareDreamRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new DreamNotFoundException("???????????? ?????? ?????? ??? ????????????"));

        shareDream.doShare();

        return DoShareDreamResponse.builder()
                .uuid(shareDream.getUuid())
                .shareDateTime(shareDream.getShareDateTime())
                .build();
    }

    @Override
    @Transactional
    public void addShareDreamLucy(String uuid) {
        ShareDream shareDream = shareDreamRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new DreamNotFoundException("???????????? ?????? ?????? ??? ????????????"));

        User user = userRepository.save(securityContextService.getPrincipal().getUser());

        shareDreamLucyPointRepository.findByUserAndShareDream(user, shareDream)
                .ifPresent(s -> { throw new ForbiddenUserException("?????? ?????????????????????"); });

        shareDreamLucyPointRepository.save(ShareDreamLucyPoint.builder()
                .user(user)
                .shareDream(shareDream)
                .build());

        shareDream.addLucy();
        shareDream.getUser().increaseLucy(1);
    }

    @Override
    @Transactional
    public void cancelShareDreamLucy(String uuid) {
        ShareDream shareDream = shareDreamRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new DreamNotFoundException("???????????? ?????? ?????? ??? ????????????"));

        User user = securityContextService.getPrincipal().getUser();

        shareDreamLucyPointRepository.findByUserAndShareDream(user, shareDream)
                .orElseThrow(() -> new ForbiddenUserException("?????? ?????????????????????"));

        shareDreamLucyPointRepository.deleteByUserAndShareDream(user, shareDream);

        shareDream.cancelLucy();
        shareDream.getUser().decreaseLucy(1);
    }
}
