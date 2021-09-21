package app.jg.og.zamong.infrastructure;

import app.jg.og.zamong.constant.UserConstant;
import app.jg.og.zamong.controller.IntegrationTest;
import app.jg.og.zamong.entity.user.User;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class JpaApplicationTest extends IntegrationTest {

    @PersistenceContext
    private EntityManager em;

    @Test
    void jpaConnectionTest() {
        User user = User.builder()
                .name(UserConstant.NAME)
                .email(UserConstant.EMAIL)
                .id(UserConstant.ID)
                .password(UserConstant.PASSWORD)
                .build();

        em.persist(user);

        UUID userId = user.getUuid();

        User persistenceUser = em.find(User.class, userId);

        assertThat(persistenceUser == user).isTrue();
    }
}
