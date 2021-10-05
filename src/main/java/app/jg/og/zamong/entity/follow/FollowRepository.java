package app.jg.og.zamong.entity.follow;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface FollowRepository extends CrudRepository<Follow, UUID> {
}
