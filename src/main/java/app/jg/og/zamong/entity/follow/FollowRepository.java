package app.jg.og.zamong.entity.follow;

import app.jg.og.zamong.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface FollowRepository extends PagingAndSortingRepository<Follow, UUID> {
    Page<Follow> findAllByFollower(User user, Pageable pageable);
    Page<Follow> findAllByFollowing(User user, Pageable pageable);
}
