package app.jg.og.zamong.learning;

import app.jg.og.zamong.entity.follow.Follow;
import app.jg.og.zamong.entity.user.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class FollowLearningTest {

    @Test
    void 팔로워() {
        addFollow(red, green);
        addFollow(red, blue);

        assertThat(red.getFollowers().stream().map(Follow::getFollower))
                .contains(green, blue);
    }

    @Test
    void 팔로잉() {
        addFollow(green, red);
        addFollow(blue, red);

        assertThat(red.getFollowings().stream().map(Follow::getFollowing))
                .contains(green, blue);
    }

    void addFollow(User following, User follower) {
        Follow follow = Follow.builder()
                .following(following)
                .follower(follower)
                .build();
        following.getFollowers().add(follow);
        follower.getFollowings().add(follow);
    }

    User red = User.builder()
            .uuid(UUID.randomUUID())
            .followings(new ArrayList<>())
            .followers(new ArrayList<>())
            .build();
    User green = User.builder()
            .uuid(UUID.randomUUID())
            .followings(new ArrayList<>())
            .followers(new ArrayList<>())
            .build();
    User blue = User.builder()
            .uuid(UUID.randomUUID())
            .followings(new ArrayList<>())
            .followers(new ArrayList<>())
            .build();
}
