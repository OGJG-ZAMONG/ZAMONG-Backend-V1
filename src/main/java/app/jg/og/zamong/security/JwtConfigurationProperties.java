package app.jg.og.zamong.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("auth.jwt")
public class JwtConfigurationProperties {

    private String secret;
    private Exp exp;

    @Getter
    @Setter
    public static class Exp {
        private Long access;
        private Long refresh;
    }
}