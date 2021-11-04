package app.jg.og.zamong.entity.dream.selldream;

import app.jg.og.zamong.entity.dream.enums.SalesStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.UUID;

public interface SellDreamRepository extends PagingAndSortingRepository<SellDream, UUID> {
    Page<SellDream> findByStatus(SalesStatus status, Pageable pageable);
    Page<SellDream> findByStatusIn(List<SalesStatus> statuses, Pageable pageable);
}
