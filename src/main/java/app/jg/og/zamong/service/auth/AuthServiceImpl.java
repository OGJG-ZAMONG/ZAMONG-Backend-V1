package app.jg.og.zamong.service.auth;

import app.jg.og.zamong.dto.request.EmailAuthenticationRequest;
import app.jg.og.zamong.dto.request.LoginUserRequest;
import app.jg.og.zamong.dto.request.SendMailRequest;
import app.jg.og.zamong.dto.request.SignUpUserRequest;
import app.jg.og.zamong.dto.response.IssueTokenResponse;
import app.jg.og.zamong.dto.response.StringResponse;
import app.jg.og.zamong.dto.response.SignedUserResponse;
import app.jg.og.zamong.entity.redis.authenticationcode.AuthenticationCode;
import app.jg.og.zamong.entity.redis.authenticationcode.AuthenticationCodeRepository;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.entity.user.UserRepository;
import app.jg.og.zamong.exception.business.BadAuthenticationCodeException;
import app.jg.og.zamong.exception.business.BadUserInformationException;
import app.jg.og.zamong.exception.business.UserIdentityDuplicationException;
import app.jg.og.zamong.security.JwtTokenProvider;
import app.jg.og.zamong.service.mail.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    private final UserRepository userRepository;
    private final AuthenticationCodeRepository authenticationCodeRepository;

    private final MailService mailService;

    @Override
    public SignedUserResponse registerUser(SignUpUserRequest request) {
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
                .build());

        return SignedUserResponse.of(user);
    }

    @Override
    public IssueTokenResponse loginUser(LoginUserRequest request) {
        User user = verifyUser(request);
        return generateIssueTokenResponse(user);
    }

    private User verifyUser(LoginUserRequest request) throws RuntimeException {
        return userRepository.findByEmailOrId(request.getUserIdentity())
                .filter(user -> passwordEncoder.matches(request.getPassword(), user.getPassword()))
                .orElseThrow(() -> new BadUserInformationException("잘못된 아이디 혹은 비밀번호입니다"));
    }

    private IssueTokenResponse generateIssueTokenResponse(User user) {
        return IssueTokenResponse.builder()
                .accessToken(jwtTokenProvider.generateAccessToken(user.getUuid().toString()))
                .refreshToken(jwtTokenProvider.generateRefreshToken(user.getUuid().toString()))
                .build();
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
