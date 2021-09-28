package app.jg.og.zamong.entity.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUuid(UUID uuid);
    Optional<User> findById(String id);
    Optional<User> findByEmail(String email);
    @Query("select u from User u where u.email = :id or u.id = :id")
    Optional<User> findByEmailOrId(@Param("id") String userId);
    Optional<User> findByEmailOrId(String email, String id);
}
