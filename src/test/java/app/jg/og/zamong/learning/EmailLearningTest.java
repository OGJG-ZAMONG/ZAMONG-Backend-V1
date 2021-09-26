package app.jg.og.zamong.learning;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class EmailLearningTest {

    public static final Random RANDOM = new Random(System.currentTimeMillis());

    @Test
    void 인증코드는_랜덤() {
        String code1 = createAuthenticationCode();
        String code2 = createAuthenticationCode();

        assertThat(code1.length()).isEqualTo(6);
        assertThat(code2.length()).isEqualTo(6);
        assertThat(code1).isNotEqualTo(code2);
    }

    @Test
    void 여섯문자_포맷팅() {
        String[] codes = createAuthenticationCode().split("");

        String result = String.format("%s%s%s%s%s%s",
                codes[0],
                codes[1],
                codes[2],
                codes[3],
                codes[4],
                codes[5]
        );
        
        assertThat(result.length()).isEqualTo(6);
    }

    private String createAuthenticationCode() {
       return String.format("%06d", RANDOM.nextInt(1000000) % 1000000);
    }

    @Test
    void 리소스파일_불러오고_조작() throws IOException {
        byte[] bytes = new ClassPathResource("application-test.yml").getInputStream().readAllBytes();

        String result = new String(bytes).replaceAll("spring", "");

        assertThat(result).doesNotContain("spring");
    }
}
