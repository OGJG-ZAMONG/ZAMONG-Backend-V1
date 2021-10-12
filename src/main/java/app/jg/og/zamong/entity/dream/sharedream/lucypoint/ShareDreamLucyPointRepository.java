package app.jg.og.zamong.entity.dream.sharedream.lucypoint;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ShareDreamLucyPointRepository extends CrudRepository<ShareDreamLucyPoint, UUID> {
}
