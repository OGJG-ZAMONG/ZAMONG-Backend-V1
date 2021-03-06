package app.jg.og.zamong.security;

import app.jg.og.zamong.exception.business.UnauthorizedTokenException;
import app.jg.og.zamong.security.auth.AuthenticationDetailService;
import app.jg.og.zamong.security.auth.AuthenticationDetails;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.regex.Pattern;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private static final String REGEX_BEARER_TOKEN = "Bearer [([a-zA-Z0-9-._~+/]+=*)]{30,600}";

    private final AuthenticationDetailService authenticationDetailService;

    private final JwtConfigurationProperties jwtConfigurationProperties;

    public String generateAccessToken(String uuid) {
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration((new Date(System.currentTimeMillis() + jwtConfigurationProperties.getExp().getAccess() * 1000)))
                .setSubject(uuid)
                .claim("type", "access")
                .signWith(SignatureAlgorithm.HS256, jwtConfigurationProperties.getSecret())
                .compact();

    }

    public String generateRefreshToken(String uuid) {
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfigurationProperties.getExp().getRefresh() * 1000))
                .setSubject(uuid)
                .claim("type", "refresh")
                .signWith(SignatureAlgorithm.HS256, jwtConfigurationProperties.getSecret())
                .compact();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION);
        if(bearerToken != null && Pattern.matches(REGEX_BEARER_TOKEN, bearerToken)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public Authentication getAuthentication(String token) {
        AuthenticationDetails authenticationDetails = authenticationDetailService.loadUserByUsername(getUserUuid(token));
        return new UsernamePasswordAuthenticationToken(authenticationDetails, "", null); // authenticationDetails.getAuthorities
    }

    public String getUserUuid(String token) {
        try {
            return Jwts.parser().setSigningKey(jwtConfigurationProperties.getSecret()).parseClaimsJws(token).getBody().getSubject();
        } catch (Exception e) {
            throw new UnauthorizedTokenException("???????????? ?????? ???????????????");
        }
    }
}
