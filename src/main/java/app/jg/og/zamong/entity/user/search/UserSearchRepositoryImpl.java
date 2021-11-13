package app.jg.og.zamong.entity.user.search;

import app.jg.og.zamong.entity.user.QUser;
import app.jg.og.zamong.entity.user.User;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class UserSearchRepositoryImpl extends QuerydslRepositorySupport implements UserSearchRepository {

    public UserSearchRepositoryImpl() {
        super(User.class);
    }

    @Override
    public List<User> findAllByUserId(String query) {
        QUser user = QUser.user;

        return from(user)
                .where(user.id.contains(query))
                .fetch();
    }
}
