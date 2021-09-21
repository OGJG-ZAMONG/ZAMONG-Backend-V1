package app.jg.og.zamong.entity.redis.authenticationcode;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationCodeRepository extends CrudRepository<AuthenticationCode, String> {
}
