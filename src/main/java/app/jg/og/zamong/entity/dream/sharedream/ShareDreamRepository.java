package app.jg.og.zamong.entity.dream.sharedream;

import app.jg.og.zamong.entity.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ShareDreamRepository extends CrudRepository<ShareDream, UUID> {
    List<ShareDream> findByUser(User user);
}
