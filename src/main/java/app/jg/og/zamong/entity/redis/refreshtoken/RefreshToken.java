package app.jg.og.zamong.entity.redis.refreshtoken;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@AllArgsConstructor
@RedisHash(timeToLive = 60 * 60 * 24 * 14)
public class RefreshToken {

    @Id
    private final String userId;

    @Indexed
    private final String refreshToken;
}
