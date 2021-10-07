package app.jg.og.zamong.util;

import app.jg.og.zamong.constant.UserConstant;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.entity.user.profile.Profile;

import java.util.UUID;

public class UserBuilder {

    public static User build() {
        return User.builder()
                .uuid(UUID.randomUUID())
                .name(UserConstant.NAME)
                .email(UserConstant.EMAIL)
                .id(UserConstant.ID)
                .password(UserConstant.PASSWORD)
                .profile(Profile.builder()
                        .host(UserConstant.PROFILE_HOST)
                        .path(UserConstant.PROFILE_PATH)
                        .build())
                .build();
    }
}
