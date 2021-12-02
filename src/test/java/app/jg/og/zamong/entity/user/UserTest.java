package app.jg.og.zamong.entity.user;

import app.jg.og.zamong.exception.business.BusinessException;
import app.jg.og.zamong.service.UnitTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserTest extends UnitTest {

    @Test
    void 루시_전달() {
        Integer initLucy = 30;
        User user = User.builder().lucyCount(initLucy).build();

        Integer lucy = 10;
        user.decreaseLucy(lucy);

        assertThat(user.getLucyCount()).isEqualTo(initLucy - lucy);
    }

    @Test
    void 루시_전달_실패() {
        Integer initLucy = 30;
        User user = User.builder().lucyCount(initLucy).build();

        Integer lucy = 50;

        assertThrows(BusinessException.class, () -> user.decreaseLucy(lucy));
    }
}
