package app.jg.og.zamong.entity.redis.findpasswordtoken;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@AllArgsConstructor
@RedisHash(timeToLive = 60)
public class FindPasswordToken {

    @Id
    private final String userId;

    @Indexed
    private final String findPasswordToken;
}
