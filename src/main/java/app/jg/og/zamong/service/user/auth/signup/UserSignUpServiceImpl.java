package app.jg.og.zamong.service.user.auth.signup;

import app.jg.og.zamong.dto.request.CheckIdDuplicationRequest;
import app.jg.og.zamong.dto.request.EmailAuthenticationRequest;
import app.jg.og.zamong.dto.request.SendMailRequest;
import app.jg.og.zamong.dto.request.SignUpUserRequest;
import app.jg.og.zamong.dto.response.SignUpUserResponse;
import app.jg.og.zamong.dto.response.StringResponse;
import app.jg.og.zamong.entity.redis.authenticationcode.AuthenticationCode;
import app.jg.og.zamong.entity.redis.authenticationcode.AuthenticationCodeRepository;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.entity.user.UserRepository;
import app.jg.og.zamong.entity.user.profile.Profile;
import app.jg.og.zamong.entity.user.profile.ProfileRepository;
import app.jg.og.zamong.exception.business.BadAuthenticationCodeException;
import app.jg.og.zamong.exception.business.UserIdentityDuplicationException;
import app.jg.og.zamong.service.mail.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@RequiredArgsConstructor
@Service
public class UserSignUpServiceImpl implements UserSignUpService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final AuthenticationCodeRepository authenticationCodeRepository;
    private final ProfileRepository profileRepository;

    private final MailService mailService;

    @Override
    public StringResponse checkIdDuplication(CheckIdDuplicationRequest request) {
        userRepository.findById(request.getId())
                .ifPresent((user) -> {
                    throw new UserIdentityDuplicationException("이미 사용중인 아이디입니다");
                });
        return new StringResponse("사용가능한 아이디입니다");
    }

    @Override
    @Transactional
    public SignUpUserResponse registerUser(SignUpUserRequest request) {
        userRepository.findByEmailOrId(request.getEmail(), request.getId())
                .ifPresent((user) -> {
                    throw new UserIdentityDuplicationException("이미 사용중인 아이디 혹은 이메일입니다");
                });

        authenticationCodeRepository.findById(request.getEmail())
                .filter(a -> request.getAuthenticationCode().equals(a.getCode()))
                .orElseThrow(() -> new BadAuthenticationCodeException("잘못된 인증코드입니다"));

        User user = this.userRepository.save(User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .id(request.getId())
                .password(passwordEncoder.encode(request.getPassword()))
                .lucyCount(0)
                .build());

        profileRepository.save(Profile.builder()
                .uuid(user.getUuid())
                .build());

        return SignUpUserResponse.of(user);
    }

    public static final Random RANDOM = new Random(System.currentTimeMillis());

    @Override
    public StringResponse sendOutAuthenticationEmail(EmailAuthenticationRequest request) {
        String authenticationCode = createAuthenticationCode();
        String emailAddress = request.getAddress();
        authenticationCodeRepository.save(new AuthenticationCode(emailAddress, authenticationCode));
        mailService.sendEmail(SendMailRequest.builder()
                .address(emailAddress)
                .authenticationCode(authenticationCode)
                .title("ZAMONG 이메일 인증 안내")
                .build()
        );
        return new StringResponse("메일을 성공적으로 보냈습니다");
    }

    private String createAuthenticationCode() {
        return String.format("%06d", RANDOM.nextInt(1000000) % 1000000);
    }
}
