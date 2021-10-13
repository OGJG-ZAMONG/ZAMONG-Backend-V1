package app.jg.og.zamong.service.user.follow;

import app.jg.og.zamong.dto.response.FollowUserResponse;
import app.jg.og.zamong.entity.follow.Follow;
import app.jg.og.zamong.entity.follow.FollowRepository;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.entity.user.UserRepository;
import app.jg.og.zamong.exception.business.UserNotFoundException;

import java.util.UUID;

public class UserFollowServiceImpl implements UserFollowService {

    UserRepository userRepository;
    FollowRepository followRepository;

    @Override
    public FollowUserResponse followUser(String uuid, String followerUuid) {
        User user = userRepository.findByUuid(UUID.fromString(uuid))
                .orElseThrow(() -> new UserNotFoundException("해당하는 유저를 찾을 수 없습니다"));
        User follower = userRepository.findByUuid(UUID.fromString(followerUuid))
                .orElseThrow(() -> new UserNotFoundException("해당하는 유저를 찾을 수 없습니다"));
        followRepository.save(Follow.builder()
                .following(user)
                .follower(follower)
                .build());
        return FollowUserResponse.builder()
                .userId(user.getUuid())
                .followerId(follower.getUuid())
                .build();
    }
}
