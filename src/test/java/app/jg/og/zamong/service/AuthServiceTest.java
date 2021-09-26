package app.jg.og.zamong.service;

import app.jg.og.zamong.constant.SecurityConstant;
import app.jg.og.zamong.constant.UserConstant;
import app.jg.og.zamong.dto.request.EmailAuthenticationRequest;
import app.jg.og.zamong.dto.request.LoginUserRequest;
import app.jg.og.zamong.dto.request.SignUpUserRequest;
import app.jg.og.zamong.dto.response.IssueTokenResponse;
import app.jg.og.zamong.dto.response.SignedUserResponse;
import app.jg.og.zamong.entity.redis.authenticationcode.AuthenticationCode;
import app.jg.og.zamong.entity.redis.authenticationcode.AuthenticationCodeRepository;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.entity.user.UserRepository;
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

import javax.mail.MessagingException;
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
    private MailService mailService;

    static private User user;

    @BeforeAll
    static void setUp() {
        user = UserBuilder.build();
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

        // when
        SignUpUserRequest request = SignUpUserRequest.builder()
                .name(name)
                .email(email)
                .authenticationCode(authenticationCode)
                .id(id)
                .password(password)
                .build();
        SignedUserResponse response = authService.registerUser(request);

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
    void 이메일_전송_실패() throws MessagingException {
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
