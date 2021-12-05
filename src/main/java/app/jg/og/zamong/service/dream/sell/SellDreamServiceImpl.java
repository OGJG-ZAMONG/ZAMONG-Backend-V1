package app.jg.og.zamong.service.dream.sell;

import app.jg.og.zamong.dto.request.dream.selldream.SellDreamCostRequest;
import app.jg.og.zamong.dto.request.dream.selldream.SellDreamRequest;
import app.jg.og.zamong.dto.response.Response;
import app.jg.og.zamong.dto.response.StringResponse;
import app.jg.og.zamong.dto.response.dream.CreateDreamResponse;
import app.jg.og.zamong.dto.response.dream.selldream.DoSellRequestDreamResponse;
import app.jg.og.zamong.dto.response.dream.selldream.SellDreamRequestGroupResponse;
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
import app.jg.og.zamong.entity.dream.selldream.chatting.room.SellDreamChattingRoom;
import app.jg.og.zamong.entity.dream.selldream.chatting.room.SellDreamChattingRoomRepository;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.exception.business.DreamNotFoundException;
import app.jg.og.zamong.exception.business.ForbiddenStatusSellDreamException;
import app.jg.og.zamong.exception.business.ForbiddenUserException;
import app.jg.og.zamong.service.file.FileSaveService;
import app.jg.og.zamong.service.securitycontext.SecurityContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SellDreamServiceImpl implements SellDreamService {

    private final SellDreamRepository sellDreamRepository;
    private final AttachmentImageRepository attachmentImageRepository;
    private final DreamTypeRepository dreamTypeRepository;
    private final SellDreamBuyRequestRepository sellDreamBuyRequestRepository;
    private final SellDreamChattingRoomRepository sellDreamChattingRoomRepository;

    private final SecurityContextService securityContextService;
    private final FileSaveService fileSaveService;

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
                .host(fileSaveService.queryHostName())
                .path(FileSaveService.DEFAULT_IMAGES[(int)(System.currentTimeMillis() % 5)])
                .dream(sellDream)
                .build());

        return CreateDreamResponse.builder()
                .uuid(sellDream.getUuid())
                .createdAt(sellDream.getCreatedAt())
                .updatedAt(sellDream.getUpdatedAt())
                .build();
    }

    @Override
    @Transactional
    public CreateDreamResponse modifySellDream(String uuid, SellDreamRequest request) {
        SellDream sellDream = sellDreamRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new DreamNotFoundException("해당하는 꿈을 찾을 수 없습니다"));

        sellDream.setTitle(request.getTitle());
        sellDream.setContent(request.getContent());
        sellDream.setCost(request.getCost());

        dreamTypeRepository.deleteByDream(sellDream);

        request.getDreamTypes()
                .forEach((dt -> dreamTypeRepository.save(DreamType.builder()
                        .dream(sellDream)
                        .code(dt)
                        .build())));

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

        try {
            sellDreamBuyRequestRepository.save(SellDreamBuyRequest.builder()
                    .user(user)
                    .sellDream(sellDream)
                    .isAccept(false)
                    .dateTime(LocalDateTime.now())
                    .build());
        } catch (RuntimeException e) {
            throw new ForbiddenStatusSellDreamException("이미 요청되었습니다");
        }

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
    public void doneSellDream(String uuid) {
        User user = securityContextService.getPrincipal().getUser();

        SellDream sellDream = sellDreamRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new DreamNotFoundException("해당하는 꿈을 찾을 수 없습니다"));

        if(!sellDream.getUser().equals(user)) {
            throw new ForbiddenUserException("완료할 수 없습니다");
        }

        sellDream.doneSale();
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

    @Override
    @Transactional
    public Response acceptSellDreamRequest(String uuid) {
        User user = securityContextService.getPrincipal().getUser();

        SellDreamBuyRequest sellDreamBuyRequest = sellDreamBuyRequestRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new DreamNotFoundException("해당하는 구매요청을 찾을 수 없습니다"));

        SellDream sellDream = sellDreamBuyRequest.getSellDream();

        if(!sellDream.getUser().equals(user)) {
            throw new ForbiddenUserException("작성자가 아닙니다");
        }

        sellDreamBuyRequest.acceptBuyRequest();

        SellDreamChattingRoom room = sellDreamChattingRoomRepository.save(SellDreamChattingRoom.builder()
                .sellDream(sellDream)
                .seller(user)
                .customer(sellDreamBuyRequest.getUser())
                .build());

        return new StringResponse(room.getUuid().toString());
    }

    @Override
    public SellDreamRequestGroupResponse querySellDreamRequests(String uuid) {
        User user = securityContextService.getPrincipal().getUser();

        SellDream sellDream = sellDreamRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new DreamNotFoundException("해당하는 꿈을 찾을 수 없습니다"));

        if(!sellDream.getUser().equals(user)) {
            throw new ForbiddenUserException("작성자가 아닙니다");
        }

        List<SellDreamBuyRequest> sellDreamBuyRequests = sellDreamBuyRequestRepository.findBySellDream(sellDream);

        return SellDreamRequestGroupResponse.builder()
                .requests(sellDreamBuyRequests.stream()
                        .map(sellDreamBuyRequest -> SellDreamRequestGroupResponse.Request.builder()
                                .uuid(sellDreamBuyRequest.getUuid())
                                .userUuid(sellDreamBuyRequest.getUser().getUuid())
                                .id(sellDreamBuyRequest.getUser().getId())
                                .profile(sellDreamBuyRequest.getUser().getProfile())
                                .isAccept(sellDreamBuyRequest.getIsAccept())
                                .requestDateTime(sellDreamBuyRequest.getDateTime())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
