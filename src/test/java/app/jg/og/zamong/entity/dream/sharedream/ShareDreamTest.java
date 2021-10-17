package app.jg.og.zamong.entity.dream.sharedream;

import app.jg.og.zamong.exception.business.AlreadySharedException;
import app.jg.og.zamong.exception.business.BusinessException;
import app.jg.og.zamong.exception.business.NotSharedException;
import app.jg.og.zamong.util.ShareDreamBuilder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class ShareDreamTest {

    @Test
    void 이미_공유한_꿈_예외() {
        ShareDream shareDream = ShareDreamBuilder.build(null);
        shareDream.setIsShared(true);

        try {
            shareDream.doShare();
            fail("No Error");
        } catch (BusinessException e) {
            assertThat(e).isInstanceOf(AlreadySharedException.class);
        }
    }

    @Test()
    void 공유되지_않은_꿈_예외() {
        ShareDream shareDream = ShareDreamBuilder.build(null);
        shareDream.setIsShared(false);

        try {
            shareDream.cancelShare();
            fail("No Error");
        } catch (BusinessException e) {
            assertThat(e).isInstanceOf(NotSharedException.class);
        }
    }
}
