package app.jg.og.zamong.entity.dream;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DreamTypeTest {

    DreamType dreamType;

    @Test
    void 꿈유형_문자열_변경() {
        dreamType = DreamType.LUCID_DREAM;

        assertThat(dreamType.toString()).isEqualTo("루시드 드림");
    }
}
