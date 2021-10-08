package app.jg.og.zamong.entity.dream.dreamtype;

import app.jg.og.zamong.entity.dream.Dream;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface DreamTypeRepository extends CrudRepository<DreamType, UUID> {
    void deleteByDream(Dream dream);
}
