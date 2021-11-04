package app.jg.og.zamong.service.dream.sell;

import app.jg.og.zamong.dto.request.dream.selldream.SellDreamCostRequest;
import app.jg.og.zamong.dto.request.dream.selldream.SellDreamRequest;
import app.jg.og.zamong.dto.response.CreateDreamResponse;
import app.jg.og.zamong.dto.response.dream.selldream.DoSellRequestDreamResponse;
import app.jg.og.zamong.entity.dream.attachment.AttachmentImage;
import app.jg.og.zamong.entity.dream.attachment.AttachmentImageRepository;
import app.jg.og.zamong.entity.dream.dreamtype.DreamType;
import app.jg.og.zamong.entity.dream.dreamtype.DreamTypeRepository;
import app.jg.og.zamong.entity.dream.enums.DreamTag;
import app.jg.og.zamong.entity.dream.enums.SalesStatus;
import app.jg.og.zamong.entity.dream.selldream.SellDream;
import app.jg.og.zamong.entity.dream.selldream.SellDreamRepository;
import app.jg.og.zamong.entity.dream.selldream.buyrequest.SellDreamBuyRequest;
import app.jg.og.zamong.entity.dream.selldream.buyrequest.SellDreamBuyRequestRepository;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.exception.business.DreamNotFoundException;
import app.jg.og.zamong.exception.business.ForbiddenUserException;
import app.jg.og.zamong.service.securitycontext.SecurityContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SellDreamServiceImpl implements SellDreamService {

    private final SellDreamRepository sellDreamRepository;
    private final AttachmentImageRepository attachmentImageRepository;
    private final DreamTypeRepository dreamTypeRepository;
    private final SellDreamBuyRequestRepository sellDreamBuyRequestRepository;

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
                .status(SalesStatus.PENDING)
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

    @Override
    public DoSellRequestDreamResponse doSellRequestDream(String uuid) {
        User user = securityContextService.getPrincipal().getUser();

        SellDream sellDream = sellDreamRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new DreamNotFoundException("해당하는 꿈을 찾을 수 없습니다"));

        sellDreamBuyRequestRepository.save(SellDreamBuyRequest.builder()
                .user(user)
                .sellDream(sellDream)
                .isAccept(false)
                .build());

        return DoSellRequestDreamResponse.builder()
                .userUuid(user.getUuid())
                .sellDreamUuid(sellDream.getUuid())
                .build();
    }

    @Override
    @Transactional
    public void patchSellDreamCost(String uuid, SellDreamCostRequest request) {
        SellDream sellDream = sellDreamRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new DreamNotFoundException("해당하는 꿈을 찾을 수 없습니다"));

        sellDream.setCost(request.getCost());
    }

    @Override
    @Transactional
    public void cancelSellDream(String uuid) {
        User user = securityContextService.getPrincipal().getUser();

        SellDream sellDream = sellDreamRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new DreamNotFoundException("해당하는 꿈을 찾을 수 없습니다"));

        if(!sellDream.getUser().equals(user)) {
            throw new ForbiddenUserException("취소할 수 없습니다");
        }

        sellDream.candleSale();
    }
}
