package app.jg.og.zamong.entity.dream.interpretationdream;

import app.jg.og.zamong.entity.dream.interpretationdream.search.InterpretationDreamSearchRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface InterpretationDreamRepository extends PagingAndSortingRepository<InterpretationDream, UUID>, InterpretationDreamSearchRepository {
}
