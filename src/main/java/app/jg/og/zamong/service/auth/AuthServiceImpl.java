package app.jg.og.zamong.service.auth;

import app.jg.og.zamong.dto.request.EmailAuthenticationRequest;
import app.jg.og.zamong.dto.request.LoginUserRequest;
import app.jg.og.zamong.dto.request.SendMailRequest;
import app.jg.og.zamong.dto.request.SignUpUserRequest;
import app.jg.og.zamong.dto.response.IssueTokenResponse;
import app.jg.og.zamong.entity.redis.authenticationcode.AuthenticationCode;
import app.jg.og.zamong.entity.redis.authenticationcode.AuthenticationCodeRepository;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.entity.user.UserRepository;
import app.jg.og.zamong.security.JwtTokenProvider;
import app.jg.og.zamong.service.mail.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
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
    public User registerUser(SignUpUserRequest request) {
        userRepository.findByEmail(request.getEmail())
                .ifPresent((user) -> {
                    throw new RuntimeException();
                });

        return this.userRepository.save(User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .id(request.getId())
                .password(passwordEncoder.encode(request.getPassword()))
                .build());
    }

    @Override
    public IssueTokenResponse loginUser(LoginUserRequest request) {
        User user = verifyUser(request);
        return generateIssueTokenResponse(user);
    }

    private User verifyUser(LoginUserRequest request) throws RuntimeException {
        return userRepository.findByEmailOrId(request.getUserIdentity())
                .filter(user -> passwordEncoder.matches(request.getPassword(), user.getPassword()))
                .orElseThrow(RuntimeException::new);
    }

    private IssueTokenResponse generateIssueTokenResponse(User user) {
        return IssueTokenResponse.builder()
                .accessToken(jwtTokenProvider.generateAccessToken(user.getUuid().toString()))
                .refreshToken(jwtTokenProvider.generateRefreshToken(user.getUuid().toString()))
                .build();
    }

    public static final Random RANDOM = new Random(System.currentTimeMillis());

    @Override
    public void sendOutAuthenticationEmail(EmailAuthenticationRequest request) {
        String authenticationCode = createAuthenticationCode();
        String emailAddress = request.getAddress();
        try {
            authenticationCodeRepository.save(new AuthenticationCode(emailAddress, authenticationCode));
            mailService.sendEmail(SendMailRequest.builder()
                    .address(emailAddress)
                    .authenticationCode(authenticationCode)
                    .title("ZAMONG 이메일 인증 안내")
                    .build()
            );
        } catch (MailException e) {
            throw new RuntimeException("이메일 전송 실패");
        }
    }

    private String createAuthenticationCode() {
        return String.format("%06d", RANDOM.nextInt(1000000) % 1000000);
    }
}
