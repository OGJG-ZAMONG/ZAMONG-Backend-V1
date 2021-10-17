package app.jg.og.zamong.service.dream.find;

import app.jg.og.zamong.dto.response.ShareDreamGroupResponse;
import app.jg.og.zamong.dto.response.ShareDreamResponse;
import app.jg.og.zamong.dto.response.SharedDreamGroupResponse;
import app.jg.og.zamong.dto.response.SharedDreamResponse;
import app.jg.og.zamong.entity.dream.sharedream.ShareDream;
import app.jg.og.zamong.entity.dream.sharedream.ShareDreamRepository;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.service.securitycontext.SecurityContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShareDreamFindServiceImpl implements ShareDreamFindService {

    private final ShareDreamRepository shareDreamRepository;
    private final SecurityContextService securityContextService;

    @Override
    public SharedDreamGroupResponse queryShareDreams(int page, int size) {
        Pageable request = PageRequest.of(page, size, Sort.by("shareDateTime").descending());
        Page<ShareDream> shareDreamPage = shareDreamRepository.findByIsSharedIsTrue(request);

        List<SharedDreamResponse> shareDreamGroup = shareDreamPage
                .map(SharedDreamResponse::of).toList();

        return SharedDreamGroupResponse.builder()
                .shareDreams(shareDreamGroup)
                .totalPage(shareDreamPage.getTotalPages())
                .totalSize(shareDreamPage.getTotalElements())
                .build();
    }

    @Override
    public ShareDreamGroupResponse queryMyShareDreams(int page, int size) {
        User user = securityContextService.getPrincipal().getUser();

        Pageable request = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<ShareDream> shareDreamPage = shareDreamRepository.findByUser(user, request);

        List<ShareDreamResponse> shareDreamGroup = shareDreamPage
                .map(sd -> ShareDreamResponse.builder()
                        .uuid(sd.getUuid())
                        .title(sd.getTitle())
                        .defaultPostingImage(sd.getDefaultImage())
                        .isShared(sd.getIsShared())
                        .createdAt(sd.getCreatedAt())
                        .build())
                .toList();

        return ShareDreamGroupResponse.builder()
                .shareDreams(shareDreamGroup)
                .totalPage(shareDreamPage.getTotalPages())
                .totalSize(shareDreamPage.getTotalElements())
                .build();
    }
}
