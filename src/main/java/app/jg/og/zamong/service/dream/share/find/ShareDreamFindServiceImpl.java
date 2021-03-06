package app.jg.og.zamong.service.dream.share.find;

import app.jg.og.zamong.dto.response.dream.sharedream.*;
import app.jg.og.zamong.entity.dream.dreamtype.DreamType;
import app.jg.og.zamong.entity.dream.sharedream.ShareDream;
import app.jg.og.zamong.entity.dream.sharedream.ShareDreamRepository;
import app.jg.og.zamong.entity.dream.sharedream.lucypoint.ShareDreamLucyPointRepository;
import app.jg.og.zamong.entity.follow.Follow;
import app.jg.og.zamong.entity.follow.FollowRepository;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.exception.business.DreamNotFoundException;
import app.jg.og.zamong.service.securitycontext.SecurityContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShareDreamFindServiceImpl implements ShareDreamFindService {

    private final ShareDreamRepository shareDreamRepository;
    private final SecurityContextService securityContextService;
    private final ShareDreamLucyPointRepository shareDreamLucyPointRepository;
    private final FollowRepository followRepository;

    @Override
    public ShareDreamInformationResponse queryShareDreamInformation(String uuid) {
        User me = securityContextService.getPrincipal().getUser();
        ShareDream shareDream = shareDreamRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new DreamNotFoundException("해당하는 꿈을 찾을 수 없습니다"));
        User user = shareDream.getUser();

        return ShareDreamInformationResponse.builder()
                .uuid(shareDream.getUuid())
                .title(shareDream.getTitle())
                .content(shareDream.getContent())
                .updatedAt(shareDream.getUpdatedAt())
                .dreamTypes(shareDream.getDreamTypes().stream()
                        .map(DreamType::toCode).collect(Collectors.toList()))
                .attachmentImage(shareDream.getDefaultImage())
                .quality(shareDream.getQuality())
                .isShared(shareDream.getIsShared())
                .sleepBeginDateTime(shareDream.getSleepDateTime())
                .sleepEndDateTime(shareDream.getSleepDateTime().plusHours(shareDream.getSleepTime()))
                .shareDateTime(shareDream.getShareDateTime())
                .lucyCount(shareDream.getLucyCount())
                .user(ShareDreamInformationResponse.User.builder()
                        .uuid(user.getUuid())
                        .id(user.getId())
                        .profile(user.getProfile())
                        .build())
                .isLiked(shareDreamLucyPointRepository.findByUserAndShareDream(me, shareDream).isPresent())
                .build();
    }

    @Override
    public SharedDreamGroupResponse queryShareDreams(int page, int size, String sort) {
        Pageable request = getSortArgument(sort, page, size);
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
    public ShareDreamGroupResponse queryMyShareDreams(int page, int size, String sort, Boolean shared) {
        User user = securityContextService.getPrincipal().getUser();

        Pageable request = getSortArgument(sort, page, size);

        List<Boolean> sharedRequest = shared ? List.of(true) : List.of(true, false);

        Page<ShareDream> shareDreamPage = shareDreamRepository.findByUserAndIsSharedIn(user, sharedRequest, request);

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
    public ShareDreamGroupResponse queryTodayMyShareDreams() {
        User user = securityContextService.getPrincipal().getUser();

        LocalDateTime start = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

        List<ShareDream> shareDreams = shareDreamRepository.findByUserAndUpdatedAtBetween(user, start, end);

        List<ShareDreamResponse> shareDreamGroup = shareDreams.stream()
                .map(sd -> ShareDreamResponse.builder()
                        .uuid(sd.getUuid())
                        .title(sd.getTitle())
                        .defaultPostingImage(sd.getDefaultImage())
                        .isShared(sd.getIsShared())
                        .createdAt(sd.getCreatedAt())
                        .build())
                .collect(Collectors.toList());

        return ShareDreamGroupResponse.builder()
                .shareDreams(shareDreamGroup)
                .build();
    }

    private Pageable getSortArgument(String sort, int page, int size) {
        switch (sort) {
            case "shared":
                return PageRequest.of(page, size, Sort.by("shareDateTime").descending());
            case "lucy":
                return PageRequest.of(page, size, Sort.by("lucyCount").descending());
            case "created":
                return PageRequest.of(page, size, Sort.by("createdAt").descending());
            default:
                throw new IllegalStateException("올바르지 않은 정렬인자");
        }
    }

    @Override
    public ShareDreamGroupResponse queryFollowShareDreams(int page, int size) {
        User user = securityContextService.getPrincipal().getUser();

        List<User> followings = followRepository.findAllByFollower(user).stream().map(Follow::getFollowing).collect(Collectors.toList());

        Pageable request = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<ShareDream> shareDreamPage = shareDreamRepository.findByUserInAndIsSharedTrue(followings, request);

        List<ShareDreamResponse> shareDreamGroup = shareDreamPage
                .map(sd -> ShareDreamResponse.builder()
                        .uuid(sd.getUuid())
                        .profile(sd.getUser().getProfile())
                        .title(sd.getTitle())
                        .defaultPostingImage(sd.getDefaultImage())
                        .isShared(sd.getIsShared())
                        .createdAt(sd.getCreatedAt())
                        .userUuid(sd.getUser().getUuid())
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
        LocalDateTime end = LocalDateTime.of(year, month, Month.of(month).maxLength(), 23, 59, 59);

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
        LocalDateTime end = LocalDateTime.of(year, month, Month.of(month).maxLength(), 23, 59, 59);

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

    @Override
    public SharedDreamGroupResponse searchShareDreams(String title, String[] types) {
        List<app.jg.og.zamong.entity.dream.enums.DreamType> dreamTypes =
                Arrays.stream(types).map(app.jg.og.zamong.entity.dream.enums.DreamType::valueOf).collect(Collectors.toList());

        List<ShareDream> shareDreams = shareDreamRepository.findByTitleLikeAndDreamTypesIn(title, dreamTypes);

        List<SharedDreamResponse> sharedDreamResponses = shareDreams
                .stream().map(SharedDreamResponse::of).collect(Collectors.toList());

        return SharedDreamGroupResponse.builder()
                .shareDreams(sharedDreamResponses)
                .build();
    }
}
