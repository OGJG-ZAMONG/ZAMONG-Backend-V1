package app.jg.og.zamong.service.user.follow;

import app.jg.og.zamong.dto.response.FollowUserResponse;
import app.jg.og.zamong.dto.response.FollowerGroupResponse;
import app.jg.og.zamong.dto.response.FollowingGroupResponse;
import app.jg.og.zamong.entity.follow.Follow;
import app.jg.og.zamong.entity.follow.FollowRepository;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.entity.user.UserRepository;
import app.jg.og.zamong.exception.business.CantFollowUserException;
import app.jg.og.zamong.exception.business.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserFollowServiceImpl implements UserFollowService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    @Override
    public FollowUserResponse followUser(String uuid, String followerUuid) {
        try {
            return doFollowUser(uuid, followerUuid);
        } catch (RuntimeException e) {
            throw new CantFollowUserException("팔로우할 수 없는 유저입니다");
        }
    }

    private FollowUserResponse doFollowUser(String uuid, String followerUuid) {
        User user = userRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new UserNotFoundException("해당하는 유저를 찾을 수 없습니다"));
        User follower = userRepository.findById(UUID.fromString(followerUuid))
                .orElseThrow(() -> new UserNotFoundException("해당하는 유저를 찾을 수 없습니다"));

        Follow follow = followRepository.save(Follow.builder()
                .following(user)
                .follower(follower)
                .followDateTime(LocalDateTime.now())
                .build());

        return FollowUserResponse.builder()
                .userId(user.getUuid())
                .followerId(follower.getUuid())
                .followDateTime(follow.getFollowDateTime())
                .build();
    }

    @Override
    public FollowingGroupResponse queryFollowings(String uuid, int page, int size) {
        User user = userRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new UserNotFoundException("해당하는 유저를 찾을 수 없습니다"));

        Pageable request = PageRequest.of(page, size, Sort.by("followDateTime").descending());
        Page<Follow> followPage = followRepository.findAllByFollower(user, request);

        List<FollowingGroupResponse.FollowingResponse> followingGroup = followPage
                .map(follow -> FollowingGroupResponse.FollowingResponse.builder()
                        .uuid(follow.getFollowing().getUuid())
                        .id(follow.getFollowing().getId())
                        .profile(follow.getFollowing().getProfile())
                        .followDateTime(follow.getFollowDateTime())
                        .isFollowing(true)
                        .build())
                .toList();

        return FollowingGroupResponse.builder()
                .followings(followingGroup)
                .totalPage(followPage.getTotalPages())
                .totalSize(followPage.getTotalElements())
                .build();
    }

    @Override
    public FollowerGroupResponse queryFollowers(String uuid, int page, int size) {
        User user = userRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new UserNotFoundException("해당하는 유저를 찾을 수 없습니다"));

        Pageable request = PageRequest.of(page, size, Sort.by("followDateTime").descending());
        Page<Follow> followPage = followRepository.findAllByFollowing(user, request);

        List<FollowerGroupResponse.FollowerResponse> followerGroup = followPage
                .map(follow -> FollowerGroupResponse.FollowerResponse.builder()
                        .uuid(follow.getFollower().getUuid())
                        .id(follow.getFollower().getId())
                        .profile(follow.getFollower().getProfile())
                        .followDateTime(follow.getFollowDateTime())
                        .isFollowing(followRepository.existsByFollowingAndFollower(follow.getFollower(), user))
                        .build())
                .toList();

        return FollowerGroupResponse.builder()
                .followers(followerGroup)
                .totalPage(followPage.getTotalPages())
                .totalSize(followPage.getTotalElements())
                .build();
    }
}
