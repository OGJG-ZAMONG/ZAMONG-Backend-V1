package app.jg.og.zamong.service.dream;

import app.jg.og.zamong.dto.request.dream.DreamContentRequest;
import app.jg.og.zamong.dto.request.dream.DreamTitleRequest;
import app.jg.og.zamong.dto.request.dream.DreamTypesRequest;
import app.jg.og.zamong.entity.dream.Dream;
import app.jg.og.zamong.entity.dream.DreamRepository;
import app.jg.og.zamong.entity.dream.attachment.AttachmentImage;
import app.jg.og.zamong.entity.dream.dreamtype.DreamType;
import app.jg.og.zamong.entity.dream.dreamtype.DreamTypeRepository;
import app.jg.og.zamong.exception.business.DreamNotFoundException;
import app.jg.og.zamong.service.file.FileSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DreamServiceImpl implements DreamService {

    private final DreamTypeRepository dreamTypeRepository;
    private final DreamRepository dreamRepository;

    private final FileSaveService fileSaveService;

    @Override
    @Transactional
    public void patchDreamTitle(String uuid, DreamTitleRequest request) {
        Dream dream = dreamRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new DreamNotFoundException("해당하는 꿈을 찾을 수 없습니다"));

        dream.setTitle(request.getTitle());
    }

    @Override
    @Transactional
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
    @Transactional
    public void patchDreamImage(String uuid, MultipartFile file) {
        Dream dream = dreamRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new DreamNotFoundException("해당하는 꿈을 찾을 수 없습니다"));

        String host = fileSaveService.queryHostName();
        String path = fileSaveService.saveFile(file, "dream/share");

        AttachmentImage attachmentImage = dream.getAttachmentImages().get(0);

        attachmentImage.setHost(host);
        attachmentImage.setPath(path);
    }

    @Override
    @Transactional
    public void removeDream(String uuid) {
        Dream dream = dreamRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new DreamNotFoundException("해당하는 꿈을 찾을 수 없습니다"));

        dreamRepository.delete(dream);
    }
}
