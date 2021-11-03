package app.jg.og.zamong.entity.dream.sharedream;

import app.jg.og.zamong.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ShareDreamRepository extends PagingAndSortingRepository<ShareDream, UUID> {
    List<ShareDream> findByUser(User user);
    List<ShareDream> findByUserAndSleepDateTimeBetween(User user, LocalDateTime start, LocalDateTime end);
    Page<ShareDream> findByIsSharedIsTrue(Pageable pageable);
    Page<ShareDream> findByUser(User user, Pageable pageable);
    Page<ShareDream> findByUserIn(List<User> users, Pageable pageable);
}
