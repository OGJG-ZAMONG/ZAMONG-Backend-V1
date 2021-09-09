package app.jg.og.zamong.service;

import app.jg.og.zamong.dto.request.LoginUserRequest;
import app.jg.og.zamong.dto.request.SignUpUserRequest;
import app.jg.og.zamong.dto.response.IssueTokenResponse;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.entity.user.UserRepository;
import app.jg.og.zamong.security.JwtTokenProvider;
import app.jg.og.zamong.service.auth.AuthService;
import app.jg.og.zamong.service.auth.AuthServiceImpl;
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
        String authenticationCode = "000000";
        String id = user.getId();
        String password = user.getPassword();

        given(userRepository.findByEmail(email)).willReturn(Optional.empty());
        given(userRepository.save(any())).willReturn(user);

        // when
        SignUpUserRequest request = SignUpUserRequest.builder()
                .name(name)
                .email(email)
                .authenticationCode(authenticationCode)
                .id(id)
                .password(password)
                .build();
        User expectUser = authService.registerUser(request);

        // then
        assertThat(expectUser).isEqualTo(user);
    }

    @Test
    void 로그인_성공() {
        //given
        String identity = user.getId();
        String uuid = user.getUuid();

        given(userRepository.findByEmailOrId(identity, identity)).willReturn(Optional.of(user));

        String password = user.getPassword();

        given(passwordEncoder.matches(password, user.getPassword())).willReturn(true);

        String accessToken = "accessToken";
        String refreshToken = "refreshToken";

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
}
