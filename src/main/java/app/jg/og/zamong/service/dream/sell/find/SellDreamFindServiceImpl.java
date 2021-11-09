package app.jg.og.zamong.service.dream.sell.find;

import app.jg.og.zamong.dto.response.dream.selldream.SellDreamGroupResponse;
import app.jg.og.zamong.dto.response.dream.selldream.SellDreamResponse;
import app.jg.og.zamong.entity.dream.dreamtype.DreamType;
import app.jg.og.zamong.entity.dream.enums.SalesStatus;
import app.jg.og.zamong.entity.dream.selldream.SellDream;
import app.jg.og.zamong.entity.dream.selldream.SellDreamRepository;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.service.securitycontext.SecurityContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SellDreamFindServiceImpl implements SellDreamFindService {

    private final SellDreamRepository sellDreamRepository;

    private final SecurityContextService securityContextService;

    @Override
    public SellDreamGroupResponse queryPendingSellDreams(int page, int size) {
        Pageable request = PageRequest.of(page, size, Sort.by("updatedAt").descending());
        Page<SellDream> sellDreamPage = sellDreamRepository.findByStatus(SalesStatus.PENDING, request);

        List<SellDreamResponse> sellDreamGroup = sellDreamResponsesOf(sellDreamPage);

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

        List<SellDreamResponse> sellDreamGroup = sellDreamResponsesOf(sellDreamPage);

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

        List<SellDreamResponse> sellDreamGroup = sellDreamResponsesOf(sellDreamPage);

        return SellDreamGroupResponse.builder()
                .sellDreams(sellDreamGroup)
                .totalPage(sellDreamPage.getTotalPages())
                .totalSize(sellDreamPage.getTotalElements())
                .build();
    }

    private List<SellDreamResponse> sellDreamResponsesOf(Page<SellDream> sellDreamPage) {
        return sellDreamPage
                .map(sd -> SellDreamResponse.builder()
                        .uuid(sd.getUuid())
                        .title(sd.getTitle())
                        .defaultPostingImage(sd.getDefaultImage())
                        .updatedAt(sd.getUpdatedAt())
                        .dreamTypes(sd.getDreamTypes().stream().map(DreamType::getCode).collect(Collectors.toList()))
                        .cost(sd.getCost())
                        .build())
                .toList();
    }
}
