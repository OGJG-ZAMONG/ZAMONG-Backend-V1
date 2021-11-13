package app.jg.og.zamong.entity.user;

import app.jg.og.zamong.entity.user.search.UserSearchRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID>, UserSearchRepository {
    Optional<User> findById(String id);
    @Query("select u from User u where u.email = :id or u.id = :id")
    Optional<User> findByEmailOrId(@Param("id") String userId);
    Optional<User> findByEmailOrId(String email, String id);
    Optional<User> findByEmail(String email);
}
