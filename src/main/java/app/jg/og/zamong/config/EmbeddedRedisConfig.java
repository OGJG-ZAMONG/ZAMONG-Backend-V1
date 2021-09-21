package app.jg.og.zamong.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Profile("test")
@Configuration
public class EmbeddedRedisConfig {

    private RedisServer redisServer;

    public EmbeddedRedisConfig(@Value("${spring.redis.port}") Integer port) {
        redisServer = new RedisServer(port);
    }

    @PostConstruct
    public void startRedis() {
        redisServer.start();
    }

    @PreDestroy
    public void stopRedis() {
        redisServer.stop();
    }
}
