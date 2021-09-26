package app.jg.og.zamong.learning;

import app.jg.og.zamong.controller.IntegrationTest;
import app.jg.og.zamong.entity.redis.authenticationcode.AuthenticationCode;
import app.jg.og.zamong.entity.redis.authenticationcode.AuthenticationCodeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.assertj.core.api.Assertions.assertThat;

public class RedisLearningTest extends IntegrationTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    void redisConnectionTest() {
        String key = "1";
        String data = "a";

        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, data);

        String result = valueOperations.get(key);
        assertThat(data).isEqualTo(result);
    }

    @Autowired
    private AuthenticationCodeRepository authenticationCodeRepository;

    @Test
    void redisRepositoryTest() {
        String emailKey = "jiwoourty@gmail.com";
        String codeData = "123456";

        AuthenticationCode code = new AuthenticationCode(emailKey, codeData);

        authenticationCodeRepository.save(code);

        AuthenticationCode result = authenticationCodeRepository.findById(emailKey)
                .orElse(new AuthenticationCode("", ""));

        assertThat(result.getCode()).isEqualTo(codeData);
    }
}
