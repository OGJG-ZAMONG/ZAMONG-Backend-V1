package app.jg.og.zamong.infrastructure;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class EmailApplicationTest {

    public static final Random RANDOM = new Random();

    @Test
    void 인증코드는_랜덤() {
        RANDOM.setSeed(System.currentTimeMillis());

        String code1 = createAuthenticationCode();
        String code2 = createAuthenticationCode();

        assertThat(code1.length()).isEqualTo(6);
        assertThat(code2.length()).isEqualTo(6);
        assertThat(code1).isNotEqualTo(code2);
    }

    private String createAuthenticationCode() {
        StringBuilder sb = new StringBuilder();
        // 한자리씩 replace 해야 됨
        for(int i=0; i<6; i++) {
            sb.append(RANDOM.nextInt(10) % 10);
        }
        return sb.toString();
    }

    @Test
    void 리소스파일_불러오고_조작() throws IOException {
        byte[] bytes = new ClassPathResource("application-test.yml").getInputStream().readAllBytes();

        String result = new String(bytes).replaceAll("spring", "");

        assertThat(result).doesNotContain("spring");
    }
}
