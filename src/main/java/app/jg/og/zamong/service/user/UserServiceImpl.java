package app.jg.og.zamong.service.user;

import app.jg.og.zamong.dto.request.FollowUserRequest;
import app.jg.og.zamong.dto.response.FollowUserResponse;
import app.jg.og.zamong.dto.response.UserInformationResponse;
import app.jg.og.zamong.entity.dream.sharedream.ShareDreamRepository;
import app.jg.og.zamong.entity.follow.Follow;
import app.jg.og.zamong.entity.follow.FollowRepository;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.entity.user.UserRepository;
import app.jg.og.zamong.exception.business.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ShareDreamRepository shareDreamRepository;
    private final FollowRepository followRepository;

    @Override
    public UserInformationResponse queryUserInformation(String uuid) {
        User user = userRepository.findByUuid(UUID.fromString(uuid))
                .orElseThrow(() -> new UserNotFoundException("해당하는 유저를 찾을 수 없습니다"));

        Integer shareDreamCount = shareDreamRepository.findByUser(user).size();

        return UserInformationResponse.builder()
                .uuid(user.getUuid())
                .name(user.getName())
                .email(user.getEmail())
                .id(user.getId())
                .profile(user.getProfile())
                .shareDreamCount(shareDreamCount)
                .lucyCount(user.getLucyCount())
                .build();
   }

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
