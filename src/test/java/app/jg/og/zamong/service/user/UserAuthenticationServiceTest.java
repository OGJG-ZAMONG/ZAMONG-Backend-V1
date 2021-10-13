package app.jg.og.zamong.service.user;

import app.jg.og.zamong.constant.SecurityConstant;
import app.jg.og.zamong.dto.request.LoginUserRequest;
import app.jg.og.zamong.dto.request.ReIssueTokenRequest;
import app.jg.og.zamong.dto.response.IssueTokenResponse;
import app.jg.og.zamong.entity.redis.authenticationcode.AuthenticationCodeRepository;
import app.jg.og.zamong.entity.redis.refreshtoken.RefreshToken;
import app.jg.og.zamong.entity.redis.refreshtoken.RefreshTokenRepository;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.entity.user.UserRepository;
import app.jg.og.zamong.security.JwtTokenProvider;
import app.jg.og.zamong.service.UnitTest;
import app.jg.og.zamong.service.user.auth.UserAuthenticationServiceImpl;
import app.jg.og.zamong.util.UserBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public class UserAuthenticationServiceTest extends UnitTest {

    @InjectMocks
    private UserAuthenticationServiceImpl userAuthenticationService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private AuthenticationCodeRepository authenticationCodeRepository;
    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    static private User user;

    @BeforeAll
    static void setUp() {
        user = UserBuilder.build();
    }

    @Test
    void 로그인_성공() {
        //given
        String identity = user.getId();
        String uuid = user.getUuid().toString();
        String password = user.getPassword();
        String accessToken = SecurityConstant.ACCESS_TOKEN;
        String refreshToken = SecurityConstant.REFRESH_TOKEN;

        given(userRepository.findByEmailOrId(identity)).willReturn(Optional.of(user));
        given(passwordEncoder.matches(password, user.getPassword())).willReturn(true);
        given(jwtTokenProvider.generateAccessToken(uuid)).willReturn(accessToken);
        given(jwtTokenProvider.generateRefreshToken(uuid)).willReturn(refreshToken);
        given(refreshTokenRepository.save(any())).willReturn(new RefreshToken(uuid, refreshToken));

        //when
        LoginUserRequest request = LoginUserRequest.builder()
                .userIdentity(identity)
                .password(password)
                .build();
        IssueTokenResponse response = userAuthenticationService.loginUser(request);

        //then
        assertThat(response.getAccessToken()).isEqualTo(accessToken);
        assertThat(response.getRefreshToken()).isEqualTo(refreshToken);
    }

    @Test
    void 토큰_재발급_성공() {
        //given
        String userId = user.getUuid().toString();
        String accessToken = SecurityConstant.ACCESS_TOKEN;
        String refreshToken = SecurityConstant.REFRESH_TOKEN;

        given(jwtTokenProvider.getUserUuid(refreshToken)).willReturn(userId);
        given(refreshTokenRepository.findById(userId)).willReturn(Optional.of(new RefreshToken(userId, refreshToken)));
        given(jwtTokenProvider.generateAccessToken(userId)).willReturn(accessToken);

        //when
        ReIssueTokenRequest request = ReIssueTokenRequest.builder()
                .refreshToken(refreshToken)
                .build();
        IssueTokenResponse response = userAuthenticationService.refreshToken(request);

        //then
        assertThat(response.getAccessToken()).isEqualTo(accessToken);
        assertThat(response.getRefreshToken()).isEqualTo(refreshToken);
    }
}
