package app.jg.og.zamong.service.dream.find;

import app.jg.og.zamong.dto.response.*;
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

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

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

    @Override
    public ShareDreamTimeTableResponse queryMyShareDreamTimeTable(int year, int month) {
        LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(year, month, Month.of(month).maxLength(), 0, 0);

        User user = securityContextService.getPrincipal().getUser();
        List<ShareDream> shareDreams = shareDreamRepository.findByUserAndSleepDateTimeBetween(user, start, end);

        ShareDreamTimeTableResponse response = ShareDreamTimeTableResponse.builder().timetables(new Hashtable<>()).build();

        shareDreams.parallelStream()
                .forEach(shareDream -> {
                    response.getTimetables().computeIfAbsent(shareDream.getSleepDateTime().toLocalDate(), k -> new Vector<>());
                    response.getTimetables().get(shareDream.getSleepDateTime().toLocalDate()).add(ShareDreamResponse.builder()
                            .uuid(shareDream.getUuid())
                            .isShared(shareDream.getIsShared())
                            .title(shareDream.getTitle())
                            .defaultPostingImage(shareDream.getDefaultImage())
                            .build());
                });
        return response;
    }

    @Override
    public ShareDreamTimeTableResponseV2 queryShareDreamTimeTableV2(int year, int month) {
        LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(year, month, Month.of(month).maxLength(), 0, 0);

        User user = securityContextService.getPrincipal().getUser();
        List<ShareDream> shareDreams = shareDreamRepository.findByUserAndSleepDateTimeBetween(user, start, end);

        ShareDreamTimeTableResponseV2 response = ShareDreamTimeTableResponseV2.builder().timetables(new ArrayList<>()).build();

        shareDreams.forEach(shareDream -> response.getTimetables().add(ShareDreamTimeTableResponseV2.ShareDreamResponse.builder()
                .uuid(shareDream.getUuid())
                .date(shareDream.getSleepDateTime().toLocalDate())
                .isShared(shareDream.getIsShared())
                .title(shareDream.getTitle())
                .createdAt(shareDream.getCreatedAt())
                .defaultPostingImage(shareDream.getDefaultImage())
                .build()));

        return response;
    }
}
