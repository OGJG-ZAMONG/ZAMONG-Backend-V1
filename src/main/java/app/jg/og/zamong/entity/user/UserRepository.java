package app.jg.og.zamong.entity.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUuid(String uuid);
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailOrId(String email, String id);
}
