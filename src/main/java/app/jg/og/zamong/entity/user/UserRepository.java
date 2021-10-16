package app.jg.og.zamong.entity.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {
    Optional<User> findById(String id);
    @Query("select u from User u where u.email = :id or u.id = :id")
    Optional<User> findByEmailOrId(@Param("id") String userId);
    Optional<User> findByEmailOrId(String email, String id);
}
