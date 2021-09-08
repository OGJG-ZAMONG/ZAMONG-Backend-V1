package app.jg.og.zamong.util;

import app.jg.og.zamong.entity.user.User;

public class UserBuilder {

    private static final String UUID = "uuid-v4-test-example";
    private static final String NAME = "name";
    private static final String EMAIL = "zamong@gmail.com";
    private static final String ID = "id";
    private static final String PASSWORD = "password";

    public static User build() {
        return User.builder()
                .uuid(UUID)
                .name(NAME)
                .email(EMAIL)
                .id(ID)
                .password(PASSWORD)
                .build();
    }
}
