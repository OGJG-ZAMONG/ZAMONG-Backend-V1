package app.jg.og.zamong.entity.dream.comment;

import app.jg.og.zamong.entity.dream.Dream;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CommentRepository extends CrudRepository<Comment, UUID> {

    Integer countAllByDream(Dream dream);
}
