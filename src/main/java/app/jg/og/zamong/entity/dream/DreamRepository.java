package app.jg.og.zamong.entity.dream;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DreamRepository extends JpaRepository<Dream, UUID> {
}
