package app.jg.og.zamong.service.dream;

import app.jg.og.zamong.dto.request.CreateShareDreamRequest;
import app.jg.og.zamong.dto.response.CreateShareDreamResponse;
import app.jg.og.zamong.entity.dream.dreamtype.DreamType;
import app.jg.og.zamong.entity.dream.dreamtype.DreamTypeRepository;
import app.jg.og.zamong.entity.dream.sharedream.ShareDream;
import app.jg.og.zamong.entity.dream.sharedream.ShareDreamRepository;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.entity.user.UserRepository;
import app.jg.og.zamong.exception.business.UserNotFoundException;
import app.jg.og.zamong.service.securitycontext.SecurityContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public CreateShareDreamResponse createShareDream(CreateShareDreamRequest request) {
        User user = userRepository.findByUuid(UUID.fromString(securityContextService.getName()))
                .orElseThrow(() -> new UserNotFoundException("해당하는 유저를 찾을 수 없습니다"));

        ShareDream shareDream = shareDreamRepository.save(ShareDream.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .user(user)
                .quality(request.getQuality())
                .isShared(false)
                .sleepDateTime(request.getSleepDateTime())
                .sleepTime(request.getSleepTime())
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
}
