package app.jg.og.zamong.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Profile("test")
@Configuration
public class EmbeddedRedisConfig {

    @Value("${spring.redis.port:6378}")
    private int redisPort;

    private static RedisServer redisServer = null;

    @PostConstruct
    public void startRedis() {
        if (redisServer == null || !redisServer.isActive()) {
            redisServer = new RedisServer(redisPort);
            redisServer.start();
        }
    }

    @PreDestroy
    public void stopRedis() {
        if (redisServer != null) {
            redisServer.stop();
        }
    }
}
