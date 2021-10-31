package app.jg.og.zamong.entity.dream.comment.recommend;

import app.jg.og.zamong.entity.dream.comment.Comment;
import app.jg.og.zamong.entity.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface RecommendRepository extends CrudRepository<Recommend, UUID> {

    Integer countAllByCommentAndRecommendType(Comment comment, RecommendType recommendType);
    Boolean existsByCommentAndUserAndRecommendType(Comment comment, User user, RecommendType recommendType);
}
