package app.jg.og.zamong.service;

import app.jg.og.zamong.constant.SecurityConstant;
import app.jg.og.zamong.constant.UserConstant;
import app.jg.og.zamong.dto.request.*;
import app.jg.og.zamong.dto.response.IssueTokenResponse;
import app.jg.og.zamong.dto.response.SignUpUserResponse;
import app.jg.og.zamong.dto.response.StringResponse;
import app.jg.og.zamong.entity.redis.authenticationcode.AuthenticationCode;
import app.jg.og.zamong.entity.redis.authenticationcode.AuthenticationCodeRepository;
import app.jg.og.zamong.entity.redis.refreshtoken.RefreshToken;
import app.jg.og.zamong.entity.redis.refreshtoken.RefreshTokenRepository;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.entity.user.UserRepository;
import app.jg.og.zamong.entity.user.profile.ProfileRepository;
import app.jg.og.zamong.security.JwtTokenProvider;
import app.jg.og.zamong.service.auth.AuthServiceImpl;
import app.jg.og.zamong.service.mail.MailService;
import app.jg.og.zamong.util.UserBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class AuthServiceTest {

    @InjectMocks
    private AuthServiceImpl authService;

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
    @Mock
    private MailService mailService;
    @Mock
    private ProfileRepository profileRepository;

    static private User user;

    @BeforeAll
    static void setUp() {
        user = UserBuilder.build();
    }

    @Test
    void 아이디_중복확인_성공() {
        //given
        String id = user.getId();

        given(userRepository.findById(id)).willReturn(Optional.empty());

        //when
        CheckIdDuplicationRequest request = CheckIdDuplicationRequest.builder()
                .id(id)
                .build();
        StringResponse response = authService.checkIdDuplication(request);

        final String successMessage = "사용가능한 아이디입니다";
        assertThat(response.getMessage()).isEqualTo(successMessage);
    }

    @Test
    void 회원가입_성공() {
        //given
        String name = user.getName();
        String email = user.getEmail();
        String id = user.getId();
        String password = user.getPassword();
        String authenticationCode = UserConstant.AUTHENTICATION_CODE;
        AuthenticationCode code = new AuthenticationCode(email, authenticationCode);

        given(userRepository.findByEmailOrId(email, id)).willReturn(Optional.empty());
        given(authenticationCodeRepository.findById(email)).willReturn(Optional.of(code));
        given(userRepository.save(any())).willReturn(UserBuilder.build());
        given(profileRepository.save(any())).willReturn(null);

        // when
        SignUpUserRequest request = SignUpUserRequest.builder()
                .name(name)
                .email(email)
                .authenticationCode(authenticationCode)
                .id(id)
                .password(password)
                .build();
        SignUpUserResponse response = authService.registerUser(request);

        // then
        assertThat(response.getUuid()).isNotNull();
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
        IssueTokenResponse response = authService.loginUser(request);

        //then
        assertThat(response.getAccessToken()).isEqualTo(accessToken);
        assertThat(response.getRefreshToken()).isEqualTo(refreshToken);
;    }

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
        IssueTokenResponse response = authService.refreshToken(request);

        //then
        assertThat(response.getAccessToken()).isEqualTo(accessToken);
        assertThat(response.getRefreshToken()).isEqualTo(refreshToken);
    }

    @Test
    void 이메일_전송_성공() {
        try {
            authService.sendOutAuthenticationEmail(EmailAuthenticationRequest.builder()
                    .address(user.getEmail())
                    .build());
        } catch (Exception e) {
            fail("이메일 전송 실패");
        }
    }

    @Test
    void 이메일_전송_실패() {
        //given
        given(mailService.sendEmail(any())).willThrow(new RuntimeException());

        //when
        Exception exception = null;
        try {
            authService.sendOutAuthenticationEmail(EmailAuthenticationRequest.builder()
                    .address(user.getEmail())
                    .build());
        } catch (RuntimeException e) {
            exception = e;
        }

        //then
        assertThat(exception).isInstanceOf(RuntimeException.class);
    }
}
