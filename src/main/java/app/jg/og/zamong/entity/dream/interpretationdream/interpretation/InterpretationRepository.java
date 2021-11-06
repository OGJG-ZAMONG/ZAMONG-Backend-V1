package app.jg.og.zamong.entity.dream.interpretationdream.interpretation;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface InterpretationRepository extends CrudRepository<Interpretation, UUID> {

    List<Interpretation> findAll();
}
