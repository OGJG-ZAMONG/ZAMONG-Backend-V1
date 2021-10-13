package app.jg.og.zamong.service.dream;

import app.jg.og.zamong.dto.request.dream.DreamContentRequest;
import app.jg.og.zamong.dto.request.dream.DreamTitleRequest;
import app.jg.og.zamong.dto.request.dream.DreamTypesRequest;
import app.jg.og.zamong.entity.dream.Dream;
import app.jg.og.zamong.entity.dream.DreamRepository;
import app.jg.og.zamong.entity.dream.dreamtype.DreamType;
import app.jg.og.zamong.entity.dream.dreamtype.DreamTypeRepository;
import app.jg.og.zamong.entity.dream.sharedream.ShareDreamRepository;
import app.jg.og.zamong.entity.user.UserRepository;
import app.jg.og.zamong.exception.business.DreamNotFoundException;
import app.jg.og.zamong.service.securitycontext.SecurityContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DreamServiceImpl implements DreamService {

    private final DreamTypeRepository dreamTypeRepository;
    private final DreamRepository dreamRepository;

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
}
