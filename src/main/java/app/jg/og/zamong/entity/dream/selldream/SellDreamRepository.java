package app.jg.og.zamong.entity.dream.selldream;

import app.jg.og.zamong.entity.dream.enums.SalesStatus;
import app.jg.og.zamong.entity.dream.selldream.search.SellDreamSearchRepository;
import app.jg.og.zamong.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.UUID;

public interface SellDreamRepository extends PagingAndSortingRepository<SellDream, UUID>, SellDreamSearchRepository {
    Page<SellDream> findByStatus(SalesStatus status, Pageable pageable);
    Page<SellDream> findByStatusAndUser(SalesStatus status, User user, Pageable pageable);
    Page<SellDream> findByUserAndStatusIn(User user, List<SalesStatus> statuses, Pageable pageable);
    List<SellDream> findByUser(User user);
}
