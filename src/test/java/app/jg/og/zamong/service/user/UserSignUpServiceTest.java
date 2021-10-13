package app.jg.og.zamong.service.user;

import app.jg.og.zamong.constant.UserConstant;
import app.jg.og.zamong.dto.request.CheckIdDuplicationRequest;
import app.jg.og.zamong.dto.request.EmailAuthenticationRequest;
import app.jg.og.zamong.dto.request.SignUpUserRequest;
import app.jg.og.zamong.dto.response.SignUpUserResponse;
import app.jg.og.zamong.dto.response.StringResponse;
import app.jg.og.zamong.entity.redis.authenticationcode.AuthenticationCode;
import app.jg.og.zamong.entity.redis.authenticationcode.AuthenticationCodeRepository;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.entity.user.UserRepository;
import app.jg.og.zamong.entity.user.profile.ProfileRepository;
import app.jg.og.zamong.service.UnitTest;
import app.jg.og.zamong.service.mail.MailService;
import app.jg.og.zamong.service.user.auth.signup.UserSignUpServiceImpl;
import app.jg.og.zamong.util.UserBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public class UserSignUpServiceTest extends UnitTest {

    @InjectMocks
    private UserSignUpServiceImpl userSignUpService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private AuthenticationCodeRepository authenticationCodeRepository;
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
        StringResponse response = userSignUpService.checkIdDuplication(request);

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
        SignUpUserResponse response = userSignUpService.registerUser(request);

        // then
        assertThat(response.getUuid()).isNotNull();
    }

    @Test
    void 이메일_전송_성공() {
        try {
            userSignUpService.sendOutAuthenticationEmail(EmailAuthenticationRequest.builder()
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
            userSignUpService.sendOutAuthenticationEmail(EmailAuthenticationRequest.builder()
                    .address(user.getEmail())
                    .build());
        } catch (RuntimeException e) {
            exception = e;
        }

        //then
        assertThat(exception).isInstanceOf(RuntimeException.class);
    }
}
