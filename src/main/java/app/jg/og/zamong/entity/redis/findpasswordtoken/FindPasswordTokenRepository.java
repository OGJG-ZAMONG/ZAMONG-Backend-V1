package app.jg.og.zamong.entity.redis.findpasswordtoken;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FindPasswordTokenRepository extends CrudRepository<FindPasswordToken, String> {
}
