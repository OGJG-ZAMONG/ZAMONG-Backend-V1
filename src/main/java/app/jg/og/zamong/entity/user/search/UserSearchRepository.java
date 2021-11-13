package app.jg.og.zamong.entity.user.search;

import app.jg.og.zamong.entity.user.User;

import java.util.List;

public interface UserSearchRepository {

    List<User> findAllByUserId(String query);
}
