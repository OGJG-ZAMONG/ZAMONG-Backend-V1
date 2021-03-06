package app.jg.og.zamong.entity.dream.sharedream;

import app.jg.og.zamong.exception.business.AlreadySharedException;
import app.jg.og.zamong.exception.business.BusinessException;
import app.jg.og.zamong.exception.business.NotSharedException;
import app.jg.og.zamong.util.ShareDreamBuilder;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

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

    @Test
    void 꿈_공유_성공() {
        LocalDateTime now = LocalDateTime.now().minusSeconds(3);
        ShareDream shareDream = ShareDreamBuilder.build(null);
        shareDream.setIsShared(false);
        shareDream.setShareDateTime(now);

        shareDream.doShare();

        assertThat(shareDream.getIsShared()).isTrue();
        assertThat(shareDream.getShareDateTime()).isAfter(now);
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

    @Test
    void 꿈_공유_취소_성공() {
        ShareDream shareDream = ShareDreamBuilder.build(null);
        shareDream.setIsShared(true);

        shareDream.cancelShare();

        assertThat(shareDream.getIsShared()).isFalse();
        assertThat(shareDream.getShareDateTime()).isNull();
    }
}
