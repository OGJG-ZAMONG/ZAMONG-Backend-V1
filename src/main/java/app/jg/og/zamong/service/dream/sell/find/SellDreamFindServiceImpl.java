package app.jg.og.zamong.service.dream.sell.find;

import app.jg.og.zamong.dto.response.dream.selldream.SellDreamGroupResponse;
import app.jg.og.zamong.dto.response.dream.selldream.SellDreamInformationResponse;
import app.jg.og.zamong.dto.response.dream.selldream.SellDreamResponse;
import app.jg.og.zamong.entity.dream.dreamtype.DreamType;
import app.jg.og.zamong.entity.dream.enums.SalesStatus;
import app.jg.og.zamong.entity.dream.selldream.SellDream;
import app.jg.og.zamong.entity.dream.selldream.SellDreamRepository;
import app.jg.og.zamong.entity.dream.selldream.buyrequest.SellDreamBuyRequestRepository;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.exception.business.DreamNotFoundException;
import app.jg.og.zamong.service.securitycontext.SecurityContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SellDreamFindServiceImpl implements SellDreamFindService {

    private final SellDreamRepository sellDreamRepository;
    private final SellDreamBuyRequestRepository sellDreamBuyRequestRepository;

    private final SecurityContextService securityContextService;

    @Override
    public SellDreamGroupResponse queryPendingSellDreams(int page, int size) {
        Pageable request = PageRequest.of(page, size, Sort.by("updatedAt").descending());
        Page<SellDream> sellDreamPage = sellDreamRepository.findByStatus(SalesStatus.PENDING, request);

        List<SellDreamResponse> sellDreamGroup = sellDreamPage
                .map(this::sellDreamResponseOf).toList();

        return SellDreamGroupResponse.builder()
                .sellDreams(sellDreamGroup)
                .totalPage(sellDreamPage.getTotalPages())
                .totalSize(sellDreamPage.getTotalElements())
                .build();
    }

    @Override
    public SellDreamGroupResponse queryMyPendingSellDreams(int page, int size) {
        User user = securityContextService.getPrincipal().getUser();

        Pageable request = PageRequest.of(page, size, Sort.by("updatedAt").descending());
        Page<SellDream> sellDreamPage = sellDreamRepository.findByStatusAndUser(SalesStatus.PENDING, user, request);

        List<SellDreamResponse> sellDreamGroup = sellDreamPage
                .map(this::sellDreamResponseOf).toList();

        return SellDreamGroupResponse.builder()
                .sellDreams(sellDreamGroup)
                .totalPage(sellDreamPage.getTotalPages())
                .totalSize(sellDreamPage.getTotalElements())
                .build();
    }

    @Override
    public SellDreamGroupResponse queryMyClosedSellDream(int page, int size) {
        User user = securityContextService.getPrincipal().getUser();

        Pageable request = PageRequest.of(page, size, Sort.by("updatedAt").descending());
        Page<SellDream> sellDreamPage = sellDreamRepository.findByUserAndStatusIn(user, List.of(SalesStatus.DONE, SalesStatus.CANCEL), request);

        List<SellDreamResponse> sellDreamGroup = sellDreamPage
                .map(this::sellDreamResponseOf).toList();

        return SellDreamGroupResponse.builder()
                .sellDreams(sellDreamGroup)
                .totalPage(sellDreamPage.getTotalPages())
                .totalSize(sellDreamPage.getTotalElements())
                .build();
    }

    @Override
    public SellDreamGroupResponse searchSellDream(String title, String[] types) {
        List<app.jg.og.zamong.entity.dream.enums.DreamType> dreamTypes =
                Arrays.stream(types).map(app.jg.og.zamong.entity.dream.enums.DreamType::valueOf).collect(Collectors.toList());

        List<SellDream> sellDreams = sellDreamRepository.findByTitleLikeAndDreamTypesIn(title, dreamTypes);

        List<SellDreamResponse> sellDreamGroup = sellDreams.stream()
                .map(this::sellDreamResponseOf)
                .collect(Collectors.toList());

        return SellDreamGroupResponse.builder()
                .sellDreams(sellDreamGroup)
                .build();
    }

    private SellDreamResponse sellDreamResponseOf(SellDream sellDream) {
        return SellDreamResponse.builder()
                .uuid(sellDream.getUuid())
                .title(sellDream.getTitle())
                .defaultPostingImage(sellDream.getDefaultImage())
                .updatedAt(sellDream.getUpdatedAt())
                .dreamTypes(sellDream.getDreamTypes().stream().map(DreamType::getCode).collect(Collectors.toList()))
                .cost(sellDream.getCost())
                .user(SellDreamResponse.User.builder()
                        .uuid(sellDream.getUser().getUuid())
                        .id(sellDream.getUser().getId())
                        .profile(sellDream.getUser().getProfile())
                        .build())
                .build();
    }

    @Override
    public SellDreamInformationResponse querySellDreamInformation(String uuid) {
        User user = securityContextService.getPrincipal().getUser();

        SellDream sellDream = sellDreamRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new DreamNotFoundException("해당하는 꿈을 찾을 수 없습니다"));

        return SellDreamInformationResponse.builder()
                .uuid(sellDream.getUuid())
                .title(sellDream.getTitle())
                .content(sellDream.getContent())
                .updatedAt(sellDream.getUpdatedAt())
                .dreamTypes(sellDream.getDreamTypes().stream().map(DreamType::getCode).collect(Collectors.toList()))
                .attachmentImage(sellDream.getDefaultImage())
                .cost(sellDream.getCost())
                .status(sellDream.getStatus())
                .isRequesting(sellDreamBuyRequestRepository.findByUserAndSellDream(user, sellDream) != null)
                .user(SellDreamInformationResponse.User.builder()
                        .uuid(sellDream.getUser().getUuid())
                        .id(sellDream.getUser().getId())
                        .profile(sellDream.getUser().getProfile())
                        .build())
                .build();
    }
}
