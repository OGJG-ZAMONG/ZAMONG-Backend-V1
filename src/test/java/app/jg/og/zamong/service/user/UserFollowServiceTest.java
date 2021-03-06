package app.jg.og.zamong.service.user;

import app.jg.og.zamong.dto.response.user.follow.FollowUserResponse;
import app.jg.og.zamong.dto.response.user.follow.FollowerGroupResponse;
import app.jg.og.zamong.dto.response.user.follow.FollowingGroupResponse;
import app.jg.og.zamong.entity.follow.Follow;
import app.jg.og.zamong.entity.follow.FollowRepository;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.entity.user.UserRepository;
import app.jg.og.zamong.exception.business.CantFollowUserException;
import app.jg.og.zamong.service.UnitTest;
import app.jg.og.zamong.service.user.follow.UserFollowServiceImpl;
import app.jg.og.zamong.util.UserBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
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
    void 유저_팔로우_실패() {
        //given
        User follower = User.builder().uuid(UUID.randomUUID()).build();
        UUID uuid = user.getUuid();
        UUID followerUuid = follower.getUuid();


        given(userRepository.findById(uuid)).willReturn(Optional.of(user));
        given(userRepository.findById(followerUuid)).willReturn(Optional.of(follower));
        given(followRepository.save(any(Follow.class))).willThrow(new RuntimeException(""));

        try {
            //when
            userFollowService.followUser(uuid.toString(), followerUuid.toString());
            fail("No Error");
        } catch (RuntimeException e) {
            //then
            assertThat(e).isInstanceOf(CantFollowUserException.class);
        }
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

    @Test
    void 팔로잉_목록() {
        //given
        int page= 0;
        int size = 1;
        UUID uuid = user.getUuid();

        Follow follow = Follow.builder()
                .following(user)
                .build();
        Page<Follow> followPage = new PageImpl(List.of(follow));

        given(userRepository.findById(uuid)).willReturn(Optional.of(user));
        given(followRepository.findAllByFollower(any(), any())).willReturn(followPage);

        //when
        FollowingGroupResponse response = userFollowService.queryFollowings(uuid.toString(), page, size);

        assertThat(response.getFollowings().get(0).getIsFollowing()).isTrue();
        assertThat(response.getFollowings().size()).isEqualTo(size);
        assertThat(response.getTotalPage()).isEqualTo(followPage.getTotalPages());
    }

    @Test
    void 팔로워_목록() {
        //given
        int page= 0;
        int size = 1;
        UUID uuid = user.getUuid();

        Follow follow = Follow.builder()
                .follower(user)
                .build();
        Page<Follow> followPage = new PageImpl(List.of(follow));

        given(userRepository.findById(uuid)).willReturn(Optional.of(user));
        given(followRepository.findAllByFollowing(any(), any())).willReturn(followPage);
        given(followRepository.existsByFollowingAndFollower(any(), any())).willReturn(true);

        //when
        FollowerGroupResponse response = userFollowService.queryFollowers(uuid.toString(), page, size);

        assertThat(response.getFollowers().get(0).getIsFollowing()).isTrue();
        assertThat(response.getFollowers().size()).isEqualTo(size);
        assertThat(response.getTotalPage()).isEqualTo(followPage.getTotalPages());
    }
}
