package app.jg.og.zamong.service.dream.find;

import app.jg.og.zamong.dto.response.ShareDreamGroupResponse;
import app.jg.og.zamong.dto.response.ShareDreamResponse;
import app.jg.og.zamong.entity.dream.dreamtype.DreamType;
import app.jg.og.zamong.entity.dream.sharedream.ShareDream;
import app.jg.og.zamong.entity.dream.sharedream.ShareDreamRepository;
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
public class ShareDreamFindServiceImpl implements ShareDreamFindService {

    private final ShareDreamRepository shareDreamRepository;

    @Override
    public ShareDreamGroupResponse queryShareDreams(int page, int size) {
        Pageable request = PageRequest.of(page, size, Sort.by("shareDateTime").descending());
        Page<ShareDream> shareDreamPage = shareDreamRepository.findByIsSharedIsTrue(request);

        List<ShareDreamResponse> shareDreamGroup = shareDreamPage
                .map(ShareDreamResponse::of).toList();

        return ShareDreamGroupResponse.builder()
                .shareDreams(shareDreamGroup)
                .totalPage(shareDreamPage.getTotalPages())
                .totalSize(shareDreamPage.getTotalElements())
                .build();
    }
}
