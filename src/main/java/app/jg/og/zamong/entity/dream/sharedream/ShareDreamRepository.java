package app.jg.og.zamong.entity.dream.sharedream;

import app.jg.og.zamong.entity.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.UUID;

public interface ShareDreamRepository extends PagingAndSortingRepository<ShareDream, UUID> {
    List<ShareDream> findByUser(User user);
    List<ShareDream> findByIsSharedIsTrue(Pageable pageable);
}
