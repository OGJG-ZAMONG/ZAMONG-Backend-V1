package app.jg.og.zamong.entity.dream.sharedream.lucypoint;

import app.jg.og.zamong.entity.dream.sharedream.ShareDream;
import app.jg.og.zamong.entity.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ShareDreamLucyPointRepository extends CrudRepository<ShareDreamLucyPoint, UUID> {

    Optional<ShareDreamLucyPoint> findByUserAndShareDream(User user, ShareDream shareDream);
}
