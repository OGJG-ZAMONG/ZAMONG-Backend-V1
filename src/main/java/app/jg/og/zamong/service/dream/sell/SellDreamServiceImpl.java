package app.jg.og.zamong.service.dream.sell;

import app.jg.og.zamong.dto.request.dream.selldream.SellDreamRequest;
import app.jg.og.zamong.dto.response.CreateDreamResponse;
import app.jg.og.zamong.entity.dream.attachment.AttachmentImage;
import app.jg.og.zamong.entity.dream.attachment.AttachmentImageRepository;
import app.jg.og.zamong.entity.dream.dreamtype.DreamType;
import app.jg.og.zamong.entity.dream.dreamtype.DreamTypeRepository;
import app.jg.og.zamong.entity.dream.enums.DreamTag;
import app.jg.og.zamong.entity.dream.selldream.SellDream;
import app.jg.og.zamong.entity.dream.selldream.SellDreamRepository;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.service.securitycontext.SecurityContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SellDreamServiceImpl implements SellDreamService {

    private final SellDreamRepository sellDreamRepository;
    private final AttachmentImageRepository attachmentImageRepository;
    private final DreamTypeRepository dreamTypeRepository;

    private final SecurityContextService securityContextService;

    @Override
    @Transactional
    public CreateDreamResponse createSellDream(SellDreamRequest request) {
        User user = securityContextService.getPrincipal().getUser();

        SellDream sellDream = sellDreamRepository.save(SellDream.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .user(user)
                .cost(request.getCost())
                .build());

        request.getDreamTypes()
                .forEach(dt -> dreamTypeRepository.save(DreamType.builder()
                        .code(dt)
                        .dream(sellDream)
                        .build()));

        attachmentImageRepository.save(AttachmentImage
                .builder()
                .tag(DreamTag.SELL_DREAM)
                .dream(sellDream)
                .build());

        return CreateDreamResponse.builder()
                .uuid(sellDream.getUuid())
                .createdAt(sellDream.getCreatedAt())
                .updatedAt(sellDream.getUpdatedAt())
                .build();
    }
}
