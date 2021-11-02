package app.jg.og.zamong.learning;

import app.jg.og.zamong.controller.IntegrationTest;
import app.jg.og.zamong.entity.dream.sharedream.ShareDream;
import app.jg.og.zamong.entity.dream.sharedream.ShareDreamRepository;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.util.ShareDreamBuilder;
import app.jg.og.zamong.util.UserBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class ShareDreamLearningTest extends IntegrationTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    ShareDreamRepository shareDreamRepository;

    @Test
    @Transactional
    void 사용자의_공유꿈_저장() {
        User user = em.merge(UserBuilder.build());
        ShareDream shareDream = ShareDreamBuilder.build(user);

        ShareDream findShareDream = shareDreamRepository.save(shareDream);

        assertThat(findShareDream.getUser()).isEqualTo(user);
    }
}
