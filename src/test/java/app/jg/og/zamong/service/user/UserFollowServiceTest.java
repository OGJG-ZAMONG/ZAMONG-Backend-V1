package app.jg.og.zamong.service.user;

import app.jg.og.zamong.dto.response.FollowUserResponse;
import app.jg.og.zamong.entity.follow.Follow;
import app.jg.og.zamong.entity.follow.FollowRepository;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.entity.user.UserRepository;
import app.jg.og.zamong.service.UnitTest;
import app.jg.og.zamong.service.user.follow.UserFollowServiceImpl;
import app.jg.og.zamong.util.UserBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public class UserFollowServiceTest extends UnitTest {

    @InjectMocks
    private UserFollowServiceImpl userFollowService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private FollowRepository followRepository;

    static private User user;

    @BeforeAll
    static void setUp() {
        user = UserBuilder.build();
    }

    @Test
    void 유저_팔로우_성공() {
        //given
        User follower = User.builder().uuid(UUID.randomUUID()).build();
        UUID uuid = user.getUuid();
        UUID followerUuid = follower.getUuid();

        LocalDateTime beforeFollow = LocalDateTime.now().minusSeconds(3);

        given(userRepository.findById(uuid)).willReturn(Optional.of(user));
        given(userRepository.findById(followerUuid)).willReturn(Optional.of(follower));
        given(followRepository.save(any(Follow.class))).willReturn(Follow.builder()
                .following(user)
                .follower(follower)
                .followDateTime(LocalDateTime.now())
                .build());

        //when
        FollowUserResponse response = userFollowService.followUser(uuid.toString(), followerUuid.toString());

        //then
        assertThat(response.getUserId()).isEqualTo(uuid);
        assertThat(response.getFollowerId()).isEqualTo(followerUuid);
        assertThat(response.getFollowDateTime()).isAfter(beforeFollow);
    }
}
