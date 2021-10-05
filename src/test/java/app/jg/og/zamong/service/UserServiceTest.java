package app.jg.og.zamong.service;

import app.jg.og.zamong.dto.response.FollowUserResponse;
import app.jg.og.zamong.dto.response.UserInformationResponse;
import app.jg.og.zamong.entity.dream.sharedream.ShareDream;
import app.jg.og.zamong.entity.dream.sharedream.ShareDreamRepository;
import app.jg.og.zamong.entity.follow.Follow;
import app.jg.og.zamong.entity.follow.FollowRepository;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.entity.user.UserRepository;
import app.jg.og.zamong.service.user.UserServiceImpl;
import app.jg.og.zamong.util.ShareDreamBuilder;
import app.jg.og.zamong.util.UserBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private ShareDreamRepository shareDreamRepository;
    @Mock
    private FollowRepository followRepository;

    static private User user;

    @BeforeAll
    static void setUp() {
        user = UserBuilder.build();
    }

    @Test
    void 유저기본정보_가져오기_성공() {
        //given
        String uuid = user.getUuid().toString();
        List<ShareDream> shareDreams = List.of(ShareDreamBuilder.build(user));
        Integer shareDreamCount = shareDreams.size();

        given(userRepository.findByUuid(UUID.fromString(uuid))).willReturn(Optional.of(user));
        given(shareDreamRepository.findByUser(user)).willReturn(shareDreams);

        //when
        UserInformationResponse response = userService.queryUserInformation(uuid);

        //then
        assertThat(response.getEmail()).isEqualTo(user.getEmail());
        assertThat(response.getShareDreamCount()).isEqualTo(shareDreamCount);
    }

    @Test
    void 유저_팔로우_성공() {
        //given
        User follower = User.builder().uuid(UUID.randomUUID()).build();
        UUID uuid = user.getUuid();
        UUID followerUuid = follower.getUuid();

        given(userRepository.findByUuid(uuid)).willReturn(Optional.of(user));
        given(userRepository.findByUuid(followerUuid)).willReturn(Optional.of(follower));
        given(followRepository.save(any(Follow.class))).willReturn(Follow.builder()
                .following(user)
                .follower(follower)
                .build());

        //when
        FollowUserResponse response = userService.followUser(uuid.toString(), followerUuid.toString());

        //then
        assertThat(response.getUserId()).isEqualTo(uuid);
        assertThat(response.getFollowerId()).isEqualTo(followerUuid);
    }
}
