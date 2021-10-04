package app.jg.og.zamong.entity.dream;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DreamRepository extends PagingAndSortingRepository<Dream, UUID> {
}
