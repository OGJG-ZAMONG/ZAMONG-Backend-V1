package app.jg.og.zamong.entity.dream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DreamRepository extends JpaRepository<Dream, UUID> {
}
