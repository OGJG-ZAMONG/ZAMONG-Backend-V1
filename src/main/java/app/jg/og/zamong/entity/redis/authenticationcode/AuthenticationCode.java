package app.jg.og.zamong.entity.redis.authenticationcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@AllArgsConstructor
@RedisHash(timeToLive = 60 * 5)
public class AuthenticationCode {

    @Id
    private final String email;

    private final String code;
}
